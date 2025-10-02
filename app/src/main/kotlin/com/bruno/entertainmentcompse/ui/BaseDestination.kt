package com.bruno.entertainmentcompse.ui

import android.net.Uri

sealed class TriviaDestination(val route: String) {
    object Category : TriviaDestination("category")

    object TriviaQuestion : TriviaDestination("trivia/{categoryId}/{difficulty}/{type}") {
        fun createRoute(categoryId: String, difficulty: String, type: String): String {
            return "trivia/${Uri.encode(categoryId)}/${Uri.encode(difficulty)}/${Uri.encode(type)}"
        }
    }
}
// "${Screen.TriviaQuestionScreen.toRoute()}/$categoryId/$difficultyEncoded/$typeEncoded"