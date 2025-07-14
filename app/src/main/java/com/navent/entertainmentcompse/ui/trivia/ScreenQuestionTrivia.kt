package com.navent.entertainmentcompse.ui.trivia

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.navent.entertainmentcompse.model.TriviaQuestion
import timber.log.Timber

@Composable
fun TriviaQuestionScreen(category: String, type: String,difficulty: String,viewModel: TriviaQuestionViewModel = hiltViewModel()) {

    val triviaQuestions by viewModel.triviaQuestions.observeAsState(emptyList())
    val isLoading = viewModel.isLoading.value


    LaunchedEffect(Unit) {
        viewModel.getDataTriviaQuestion(category = category, type = type,difficulty = difficulty)
        Timber.d("TriviaQuestionScreen - Le estamos enviando: category=$category, type=$type, difficulty=$difficulty")
    }

    if (isLoading) {
        CircularProgressIndicator()
    } else {
        LazyColumn {
            items(triviaQuestions) { question ->
                TriviaQuestionItem(question = question)
            }
        }
    }

}

@Composable
fun TriviaQuestionItem(question: TriviaQuestion) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = question.question, style = MaterialTheme.typography.bodyLarge)
        Text(text = "Category: ${question.category}", style = MaterialTheme.typography.bodyLarge)
        Text(
            text = "Difficulty: ${question.difficulty}",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

val fakeQuestions = listOf(
    TriviaQuestion(
        id = 1,
        category = "Geography",
        type = "multiple",
        difficulty = "easy",
        question = "What is the capital of France?",
        createdBy = "MockUser"
    ),
    TriviaQuestion(
        id = 2,
        category = "Science",
        type = "multiple",
        difficulty = "medium",
        question = "Which planet is known as the Red Planet?",
        createdBy = "TestUser"
    ),
    TriviaQuestion(
        id = 3,
        category = "Art",
        type = "multiple",
        difficulty = "hard",
        question = "Who painted the Mona Lisa?",
        createdBy = "System"
    )
)

@Preview(showBackground = true)
@Composable
fun PreviewTriviaQuestionList() {
    LazyColumn {
        items(fakeQuestions) { question ->
            TriviaQuestionItem(question = question)
        }
    }
}

