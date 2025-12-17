package com.bruno.entertainmentcompse.ui.navigation

import androidx.navigation.NavController
import com.bruno.entertainmentcompse.ui.select.mapTypeToApiValue
import com.bruno.entertainmentcompse.ui.select.viewmodel.CategoryUiState
import timber.log.Timber

class NavigatorHelper(
    private val navController: NavController
) {

    fun goToTrivia(uiState: CategoryUiState) {
        val categoryId = uiState.selectedCategory?.id?.toString()
        val difficulty = uiState.selectedDifficulty
        val type = uiState.selectedType?.let { mapTypeToApiValue(it) }

        if (categoryId.isNullOrBlank() || difficulty.isNullOrBlank() || type.isNullOrBlank()) {
            showError("Faltan parámetros para navegar")
            return
        }

        navController.navigate(
            Screen.TriviaQuestion(
                categoryId = categoryId,
                difficulty = difficulty,
                type = type
            )
        )

    }

    private fun showError(message: String) {
        Timber.e(message)
    }
}
