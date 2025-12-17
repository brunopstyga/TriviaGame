package com.bruno.entertainmentcompse.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.bruno.entertainmentcompse.ui.TriviaDestination
import com.bruno.entertainmentcompse.ui.select.CategoryScreen
import com.bruno.entertainmentcompse.ui.trivia.TriviaQuestionScreen

@Suppress("FunctionNaming")
@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navigator: NavigatorHelper,
    onTitleChange: (String) -> Unit,
    onShowBackButton: (Boolean) -> Unit
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.CategoryScreen
    ) {

        composable<Screen.CategoryScreen> {
            CategoryScreen(
                navigatorHelper = navigator,
                onTitleChange = onTitleChange,
                onShowBackButton = onShowBackButton
            )
        }

        composable<Screen.TriviaQuestion> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.TriviaQuestion>()
            TriviaQuestionScreen(
                category = args.categoryId,
                difficulty = args.difficulty,
                type = args.type,
                onTitleChange = onTitleChange,
                onShowBackButton = onShowBackButton,
            )

        }
    }
}

