package com.navent.entertainmentcompse.commons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.navent.entertainmentcompse.model.TriviaQuestion

@Composable
fun TriviaQuestionCard(
    triviaQuestion: TriviaQuestion,
    answers: List<String>,
    selectedAnswer: String,
    onAnswerSelected: (String) -> Unit,
    onSubmitClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = triviaQuestion.category,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = triviaQuestion.question,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Radio buttons (simulate RadioGroup)
            Column(modifier = Modifier.padding(bottom = 16.dp)) {
                answers.forEach { answer ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onAnswerSelected(answer) }
                            .padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = selectedAnswer == answer,
                            onClick = { onAnswerSelected(answer) }
                        )
                        Text(
                            text = answer,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }

            Button(
                onClick = onSubmitClick,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Aceptar")
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

    TriviaQuestionCard(
        triviaQuestion = sampleQuestion,
        answers = mockAnswers,
        selectedAnswer = selectedAnswer,
        onAnswerSelected = { selectedAnswer = it },
        onSubmitClick = { /* Acción mock */ }
    )
}
