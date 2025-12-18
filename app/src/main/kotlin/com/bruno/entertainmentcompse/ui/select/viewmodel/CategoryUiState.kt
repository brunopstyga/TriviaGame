package com.bruno.entertainmentcompse.ui.select.viewmodel

import com.bruno.entertainmentcompse.data.remote.Category
import com.bruno.entertainmentcompse.data.remote.TriviaQuestion

data class CategoryUiState(
    val isLoading: Boolean = false,
    val triviaQuestions: List<TriviaQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val selectedAnswer: String = "",
    val showResult: Boolean = false,
    val categories: List<Category> = emptyList(),
    val selectedDifficulty: String? = null,
    val selectedType: String? = null,
    val selectedAmount: Int? = null,
    val selectedCategory: Category? = null,
    val gameFinished: Boolean = false,
    var showDialog: Boolean = false,
    var showGame: Boolean = false,
    val error: String? = null
){
    val currentQuestion: TriviaQuestion?
        get() = triviaQuestions.getOrNull(currentIndex)

    val isLastQuestion: Boolean
        get() = currentIndex == triviaQuestions.lastIndex

    val isCorrect: Boolean
        get() = selectedAnswer.isNotEmpty() &&
                selectedAnswer == currentQuestion?.correctAnswer
}

