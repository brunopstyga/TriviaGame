package com.bruno.entertainmentcompse.ui.trivia

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bruno.entertainmentcompse.R
import com.bruno.entertainmentcompse.model.TriviaQuestion
import com.bruno.entertainmentcompse.util.characterDecode

@Composable
fun TriviaQuestionScreen(category: String,
                         type: String,
                         difficulty: String,
                         viewModel: TriviaQuestionViewModel = hiltViewModel(),
                         onTitleChange: (String) -> Unit,
                         onShowBackButton: (Boolean) -> Unit) {

    val state by viewModel.uiState.collectAsState()

    val context = LocalContext.current
    val title = context.getString(R.string.info_trivia)

    LaunchedEffect(Unit) {
        onTitleChange(title)
        onShowBackButton(true)
        viewModel.getDataTriviaQuestion(category = category, type = type,difficulty = difficulty)
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp),
                strokeWidth = 6.dp
            )
        }
    } else {
        LazyColumn {
            items(state.triviaQuestions) { question ->
                TriviaQuestionItem(question = question)
            }
        }
    }

}

@Composable
fun TriviaQuestionItem(question: TriviaQuestion) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = characterDecode(question.question), style = MaterialTheme.typography.bodyLarge)
        Text(text = "Category: ${characterDecode(question.category)}", style = MaterialTheme.typography.bodyLarge)
        Text(
            text = "Difficulty: ${characterDecode(question.difficulty)}",
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
        createdBy = "MockUser",
        correctAnswer = "Paris",
        incorrectAnswers = listOf("London", "Berlin", "Madrid")
    ),
    TriviaQuestion(
        id = 2,
        category = "Science",
        type = "multiple",
        difficulty = "medium",
        question = "Which planet is known as the Red Planet?",
        createdBy = "TestUser",
        correctAnswer = "Mars",
        incorrectAnswers = listOf("Venus", "Jupiter", "Saturn")
    ),
    TriviaQuestion(
        id = 3,
        category = "Art",
        type = "multiple",
        difficulty = "hard",
        question = "Who painted the Mona Lisa?",
        createdBy = "System",
        correctAnswer = "Leonardo da Vinci",
        incorrectAnswers = listOf("Pablo Picasso", "Vincent van Gogh", "Michelangelo")
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

