package com.bruno.entertainmentcompse.ui.trivia

import com.bruno.entertainmentcompse.data.remote.TriviaQuestion

data class TriviaQuestionUIState(
    val triviaQuestions: List<TriviaQuestion> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
