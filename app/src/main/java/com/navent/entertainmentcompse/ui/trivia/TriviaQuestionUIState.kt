package com.navent.entertainmentcompse.ui.trivia

import com.navent.entertainmentcompse.model.TriviaQuestion

data class TriviaQuestionUIState(
    val triviaQuestions: List<TriviaQuestion> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
