package com.navent.entertainmentcompse.ui.select


import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.navent.entertainmentcompse.model.Category
import com.navent.entertainmentcompse.navigation.Screen
import com.navent.entertainmentcompse.navigation.toRoute
import com.navent.entertainmentcompse.commons.Select
import com.navent.entertainmentcompse.ui.select.viewmodel.GameViewModel
import com.navent.entertainmentcompse.util.AlertGameDialog
import timber.log.Timber


@Composable
fun CategoryScreen(viewModel: GameViewModel = hiltViewModel(),
                   navController: NavHostController,
                   onTitleChange: (String) -> Unit) {

    val isLoading = viewModel.isLoading.value

    val categories = viewModel.gameCategories.observeAsState(initial = emptyList())

    val difficulty by viewModel.difficulty.observeAsState()

    val type by viewModel.type.observeAsState()

    var showDialog by remember { mutableStateOf(false) }

    val selectedCategory by viewModel.selectedCategory.observeAsState()

    val amount by viewModel.selectedAmount.observeAsState()

    val triviaQuestions by viewModel.triviaQuestions.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        onTitleChange("Inicio")
        viewModel.getDataCategories()
    }

    Scaffold ( modifier = Modifier.fillMaxSize(),
        containerColor = Color.Gray)
    { innerPadding ->
        if (isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp)
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {

                Select(
                    items = categories.value,
                    selectedItem = selectedCategory,
                    onItemSelected = { category ->
                        viewModel.setSelectedCategory(category)
                        Timber.d("CategoryScreen", "Categoría seleccionada: ${category.id}")
                    },
                    itemToString = { it.name ?: "Sin nombre" },
                    placeholder = "Selecciona una categoría"
                )

                Spacer(modifier = Modifier.height(16.dp))

                Difficulty(
                    selectedDifficulty = difficulty,
                    onDifficultySelected = { difficulty ->
                        viewModel.setSelectedDifficulty(difficulty)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Type(
                    selectedDifficulty = type,
                    onDifficultySelected = { type ->
                        viewModel.setSelectedType(type)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val isFormComplete = viewModel.startGame()
                        showDialog = !isFormComplete

                        if (isFormComplete) {

                            val categoryId = Uri.encode((selectedCategory?.id ?: "").toString())
                            val difficultyEncoded = Uri.encode(difficulty ?: "")
                            val typeEncoded = Uri.encode(type?.let { mapTypeToApiValue(it) })

                            val route = "${Screen.TriviaQuestionScreen.toRoute()}/$categoryId/$difficultyEncoded/$typeEncoded"

                            navController.navigate(route)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Info Trivia")
                }

                Spacer(modifier = Modifier.height(16.dp))

                NumberSelector(
                    selectedNumber = amount,
                    onNumberSelected = { viewModel.setSelectedAmount(it) }
                )

                Button(
                    onClick = {
                          viewModel.getTrivia(amount ?: 10, selectedCategory?.id ?: 0)
                        triviaQuestions
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Jugar")
                }
                if (showDialog) {
                    AlertGameDialog(
                        title = "Atención",
                        message = "Debes completar todos los campos para continuar.",
                        onDismiss = { showDialog = false },
                        confirmText = "OK"
                    )
                }

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


@Composable
fun CategoryItem(category: Category, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = category.name ?: "Sin nombre",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.clickable {
                category.id?.let { id ->
                    navController.navigate("${Screen.TriviaQuestionScreen.toRoute()}/$id")
                    Log.d("NavigationGraph", "categoryId: $id")
                }
            }
        )
//        Text(
//            text = "ID: ${category.id ?: "Desconocido"}",
//            style = MaterialTheme.typography.bodyMedium
//        )
    }
}



