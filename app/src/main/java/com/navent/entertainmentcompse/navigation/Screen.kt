package com.navent.entertainmentcompse.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object GameScreen : Screen()

    @Serializable
    data object CategoryScreen : Screen()


    @Serializable
    data object TriviaQuestionScreen : Screen()
}


fun Screen.toRoute(): String {
    return when (this) {
        is Screen.GameScreen -> "game_screen"
        is Screen.CategoryScreen -> "category_screen"
        is Screen.TriviaQuestionScreen -> "trivia_question_screen"
    }
}
