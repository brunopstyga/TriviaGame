package com.navent.entertainmentcompse.ui.select


import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.navent.entertainmentcompse.R
import com.navent.entertainmentcompse.commons.Select
import com.navent.entertainmentcompse.commons.TriviaGame
import com.navent.entertainmentcompse.model.Category
import com.navent.entertainmentcompse.navigation.Screen
import com.navent.entertainmentcompse.navigation.toRoute
import com.navent.entertainmentcompse.ui.select.viewmodel.GameViewModel
import com.navent.entertainmentcompse.util.AlertGameDialog


@Composable
fun CategoryScreen(
    viewModel: GameViewModel = hiltViewModel(),
    navController: NavHostController,
    onTitleChange: (String) -> Unit
) {

    val context = LocalContext.current
    val title = context.getString(R.string.screen_title)

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        onTitleChange(title)
        viewModel.getDataCategories()
        navController.currentBackStackEntryFlow.collect { backEntry ->
            if (backEntry.destination.route == Screen.CategoryScreen.toRoute()) {
                uiState.showGame = false
                viewModel.resetGame()
            }
        }
    }

    LaunchedEffect(uiState.triviaQuestions) {
        if (uiState.triviaQuestions.isNotEmpty()) {
            uiState.showGame = true
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Gray
    )
    { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            Text(
                text = stringResource(R.string.configure_items),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(26.dp))

            Select(
                items = uiState.categories,
                selectedItem = uiState.selectedCategory,
                onItemSelected = { category ->
                    viewModel.setSelectedCategory(category)
                },
                itemToString = { it.name ?: "Sin nombre" },
                placeholder = stringResource(R.string.select_category)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Difficulty(
                selectedDifficulty = uiState.selectedDifficulty,
                onDifficultySelected = { difficulty ->
                    viewModel.setSelectedDifficulty(difficulty)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Type(
                selectedDifficulty = uiState.selectedType,
                onDifficultySelected = { type ->
                    viewModel.setSelectedType(type)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {

                    val categoryId = Uri.encode((uiState.selectedCategory?.id ?: "").toString())
                    val difficultyEncoded = Uri.encode(uiState.selectedDifficulty ?: "")
                    val typeEncoded = Uri.encode(uiState.selectedType?.let { mapTypeToApiValue(it) })

                    val route =
                        "${Screen.TriviaQuestionScreen.toRoute()}/$categoryId/$difficultyEncoded/$typeEncoded"

                    navController.navigate(route)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.gameFinished,
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.DarkGray
                )
            ) {
                Text(stringResource(R.string.info_trivia))
            }

            Spacer(modifier = Modifier.height(26.dp))

            NumberSelector(
                selectedNumber = uiState.selectedAmount,
                onNumberSelected = { viewModel.setSelectedAmount(it) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val isFormComplete = viewModel.startGame()
                    uiState.showDialog = !isFormComplete

                    if (isFormComplete) {
                        viewModel.getTrivia(uiState.selectedAmount ?: 10, uiState.selectedCategory?.id ?: 0)
                    }

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.play))
            }
            if (uiState.showGame && uiState.triviaQuestions.isNotEmpty()) {
                    TriviaGame(
                        triviaQuestions = uiState.triviaQuestions,
                        onGameFinished = {
                            uiState.showGame = false
                            viewModel.setGameFinished(true)
                        }
                    )
            }
            if (uiState.showDialog) {
                AlertGameDialog(
                    title = stringResource(R.string.atenttion),
                    message = stringResource(R.string.complete_fields),
                    onDismiss = { uiState.showDialog = false },
                    confirmText = stringResource(R.string.ok)
                )
            }

            Spacer(modifier = Modifier.height(26.dp))

            Button(
                onClick = {
                    viewModel.resetGame()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.reset))
            }

        }

    }
}

fun mapTypeToApiValue(type: String): String {
    return when (type) {
        "Multiple Choice" -> "multiple"
        "True/False" -> "multiple"
        "Any Type" -> ""
        else -> type.lowercase()
    }
}

@Preview(showBackground = true)
@Composable
fun SelectPreview() {
    // Datos mockeados
    val categories = listOf(
        Category(id = 9, name = "General Knowledge"),
        Category(id = 10, name = "Books"),
        Category(id = 11, name = "Film")
    )
    val difficulties = listOf("easy", "medium", "hard")
    val types = listOf("multiple", "boolean")

    // Estados locales para la preview
    var selectedCategory by remember { mutableStateOf<Category?>(categories[0]) }
    var selectedDifficulty by remember { mutableStateOf<String?>(difficulties[0]) }
    var selectedType by remember { mutableStateOf<String?>(types[0]) }

    Column(modifier = Modifier.padding(16.dp)) {

        Select(
            items = categories,
            selectedItem = selectedCategory,
            onItemSelected = { selectedCategory = it },
            itemToString = { it.name ?: "Sin nombre" },
            placeholder = "Selecciona una categoría"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Select(
            items = difficulties,
            selectedItem = selectedDifficulty,
            onItemSelected = { selectedDifficulty = it },
            itemToString = { it.replaceFirstChar { it.uppercaseChar() } },
            placeholder = "Selecciona una dificultad"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Select(
            items = types,
            selectedItem = selectedType,
            onItemSelected = { selectedType = it },
            itemToString = { it.replaceFirstChar { it.uppercaseChar() } },
            placeholder = "Selecciona un tipo"
        )
    }
}



