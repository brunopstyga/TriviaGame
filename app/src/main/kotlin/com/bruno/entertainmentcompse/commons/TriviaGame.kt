package com.bruno.entertainmentcompse.commons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.bruno.entertainmentcompse.R
import com.bruno.entertainmentcompse.model.TriviaQuestion
import com.bruno.entertainmentcompse.util.AlertGameDialog
import com.bruno.entertainmentcompse.util.characterDecode



@Composable
fun TriviaGame(
    triviaQuestions: List<TriviaQuestion>,
    onGameFinished: () -> Unit
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedAnswer by remember { mutableStateOf("") }
    var showResult by remember { mutableStateOf(false) }

    val currentQuestion = triviaQuestions.getOrNull(currentIndex) ?: run {
        onGameFinished()
        return
    }

    val allAnswers = remember(currentQuestion) {
        (currentQuestion.incorrectAnswers + currentQuestion.correctAnswer).shuffled()
    }

    TriviaQuestionDialog(
        triviaQuestion = currentQuestion,
        answers = allAnswers,
        selectedAnswer = selectedAnswer,
        onAnswerSelected = { selectedAnswer = it },
        onSubmit = {
            showResult = true
        },
        onDismissRequest = {}
    )

    val isCorrect = selectedAnswer == currentQuestion.correctAnswer

    if (showResult) {
        val correctAnswerMessage = stringResource(
            id = R.string.correct_answer_was,
            characterDecode(currentQuestion.correctAnswer)
        )
        AlertGameDialog(
            title = if (isCorrect) stringResource(R.string.correct) else stringResource(R.string.incorrect),
            message = if (isCorrect)
                stringResource(R.string.well_done)
            else
                correctAnswerMessage,
            onDismiss = {
                showResult = false
                selectedAnswer = ""

                if (currentIndex + 1 < triviaQuestions.size) {
                    currentIndex++
                } else {
                    onGameFinished()
                }
            },
            confirmText = stringResource(R.string.next)
        )
    }
}




