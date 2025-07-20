package com.bruno.entertainmentcompse.ui.select.viewmodel

import com.bruno.entertainmentcompse.model.Category
import com.bruno.entertainmentcompse.model.TriviaQuestion

data class CategoryUiState(
    val isLoading: Boolean = false,
    val triviaQuestions: List<TriviaQuestion> = emptyList(),
    val categories: List<Category> = emptyList(),
    val selectedDifficulty: String? = null,
    val selectedType: String? = null,
    val selectedAmount: Int? = null,
    val selectedCategory: Category? = null,
    val gameFinished: Boolean = false,
    var showDialog: Boolean = false,
    var showGame: Boolean = false
)

