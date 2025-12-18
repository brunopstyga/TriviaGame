package com.bruno.entertainmentcompse.ui.commons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.bruno.entertainmentcompse.R
import com.bruno.entertainmentcompse.data.remote.TriviaQuestion
import com.bruno.entertainmentcompse.ui.select.viewmodel.CategoryUiState
import com.bruno.entertainmentcompse.util.AlertGameDialog
import com.bruno.entertainmentcompse.util.characterDecode



@Composable
fun TriviaGame(
    uiState: CategoryUiState,
    onAnswerSelected: (String) -> Unit,
    onSubmit: () -> Unit,
    onNext: () -> Unit
) {
    val question = uiState.currentQuestion ?: return

    val answers = remember(question) {
        (question.incorrectAnswers + question.correctAnswer).shuffled()
    }

    TriviaQuestionDialog(
        triviaQuestion = question,
        answers = answers,
        selectedAnswer = uiState.selectedAnswer,
        onAnswerSelected = onAnswerSelected,
        onSubmit =onSubmit,
        onDismissRequest = {}
    )

    if (uiState.showResult) {
        AlertGameDialog(
            title = if (uiState.isCorrect)
                stringResource(R.string.correct)
            else
                stringResource(R.string.incorrect),

            message = if (uiState.isCorrect)
                stringResource(R.string.well_done)
            else
                stringResource(
                    R.string.correct_answer_was,
                    characterDecode(question.correctAnswer)
                ),

            onDismiss = onNext,
            confirmText = stringResource(R.string.next)
        )
    }
}