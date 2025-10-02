package com.bruno.entertainmentcompse.navigation

import android.content.Context
import androidx.navigation.NavController
import com.bruno.entertainmentcompse.ui.TriviaDestination
import com.bruno.entertainmentcompse.ui.select.mapTypeToApiValue
import com.bruno.entertainmentcompse.ui.select.viewmodel.CategoryUiState
import timber.log.Timber

class NavigatorHelper(
    private val navController: NavController,
    private val context: Context
) {
    fun navigateTo(route: String) {
        try {
            navController.navigate(route)
        } catch (e: Exception) {
            showError("Error al navegar: ${e.message}")
        }
    }

    fun goToTrivia(uiState: CategoryUiState) {
        val categoryId = uiState.selectedCategory?.id?.toString()
        val difficulty = uiState.selectedDifficulty
        val type = uiState.selectedType?.let { mapTypeToApiValue(it) }

        if (categoryId.isNullOrBlank() || difficulty.isNullOrBlank() || type.isNullOrBlank()) {
            showError("Faltan parámetros para navegar")
            return
        }

        val route = TriviaDestination.TriviaQuestion.createRoute(categoryId, difficulty, type)
        navigateTo(route)
    }

    private fun showError(message: String) {
        Timber.e(message)
    }
}
