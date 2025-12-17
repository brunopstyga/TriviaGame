package com.bruno.entertainmentcompse.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {

    @Serializable
    data object GameScreen : Screen

    @Serializable
    data object CategoryScreen : Screen

    @Serializable
    data class TriviaQuestion(
        val categoryId: String,
        val difficulty: String,
        val type: String
    ) : Screen
}

