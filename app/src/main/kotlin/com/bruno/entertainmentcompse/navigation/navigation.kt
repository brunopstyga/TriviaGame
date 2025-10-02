package com.bruno.entertainmentcompse.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bruno.entertainmentcompse.ui.TriviaDestination
import com.bruno.entertainmentcompse.ui.select.CategoryScreen
import com.bruno.entertainmentcompse.ui.trivia.TriviaQuestionScreen

@Suppress("FunctionNaming")
@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onTitleChange: (String) -> Unit,
    onShowBackButton: (Boolean) -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.CategoryScreen.toRoute()
    ) {

        composable(Screen.CategoryScreen.toRoute()) {
            CategoryScreen(
                navController = navController,
                onTitleChange = onTitleChange,
                onShowBackButton = onShowBackButton
            )
        }
        composable(
            route = TriviaDestination.TriviaQuestion.route,
            arguments = listOf(
                navArgument("categoryId") { type = NavType.StringType },
                navArgument("difficulty") { type = NavType.StringType },
                navArgument("type") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("categoryId").orEmpty()
            val difficulty = backStackEntry.arguments?.getString("difficulty").orEmpty()
            val type = backStackEntry.arguments?.getString("type").orEmpty()
            TriviaQuestionScreen(
                category = category,
                difficulty = difficulty,
                type = type,
                onTitleChange = onTitleChange,
                onShowBackButton = onShowBackButton,
            )
        }
    }
}

