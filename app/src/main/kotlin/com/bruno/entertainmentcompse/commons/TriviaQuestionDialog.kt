package com.bruno.entertainmentcompse.commons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bruno.entertainmentcompse.R
import com.bruno.entertainmentcompse.model.TriviaQuestion
import com.bruno.entertainmentcompse.util.characterDecode

@Composable
fun TriviaQuestionDialog(
    triviaQuestion: TriviaQuestion,
    answers: List<String>,
    selectedAnswer: String,
    onAnswerSelected: (String) -> Unit,
    onSubmit: () -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp,
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = characterDecode(triviaQuestion.category),
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = characterDecode(triviaQuestion.question),
                    style = MaterialTheme.typography.bodyLarge
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    answers.forEach { answer ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onAnswerSelected(answer) }
                        ) {
                            RadioButton(
                                selected = selectedAnswer == answer,
                                onClick = { onAnswerSelected(answer) }
                            )
                            Text(
                                text = characterDecode(answer),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }

                Button(
                    onClick = onSubmit,
                    enabled = selectedAnswer.isNotEmpty(),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = stringResource(R.string.accept))
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TriviaQuestionCardPreview() {
    val sampleQuestion = TriviaQuestion(
        id = 1,
        category = "Ciencia",
        type = "multiple",
        difficulty = "easy",
        question = "¿Cuál es el planeta más cercano al sol?",
        createdBy = "Mock",
        correctAnswer = "Mercurio",
        incorrectAnswers = listOf("Venus", "Tierra", "Marte")
    )

    val mockAnswers = listOf("Mercurio", "Venus", "Tierra", "Marte")
    var selectedAnswer by remember { mutableStateOf("Mercurio") }

    TriviaQuestionDialog(
        triviaQuestion = sampleQuestion,
        answers = mockAnswers,
        selectedAnswer = selectedAnswer,
        onAnswerSelected = { selectedAnswer = it },
        onSubmit = {},
        onDismissRequest = {}
    )
}
