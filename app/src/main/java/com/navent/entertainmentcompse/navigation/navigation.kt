package com.navent.entertainmentcompse.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.navent.entertainmentcompse.ui.select.CategoryScreen
import com.navent.entertainmentcompse.ui.trivia.TriviaQuestionScreen

@Suppress("FunctionNaming")
@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onTitleChange: (String) -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.CategoryScreen.toRoute()
    ) {

        composable(Screen.CategoryScreen.toRoute()) {
            CategoryScreen(navController = navController,
                onTitleChange = onTitleChange
            )
        }
        composable(
            route = "${Screen.TriviaQuestionScreen.toRoute()}/{categoryId}/{difficulty}/{type}",
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
                onTitleChange = onTitleChange
            )

        }
    }

}

