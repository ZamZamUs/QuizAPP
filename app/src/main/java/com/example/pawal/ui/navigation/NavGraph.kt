package com.example.pawal.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pawal.ui.screens.*
import com.example.pawal.viewmodel.QuizViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: QuizViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onCategoryClick = { id ->
                    navController.navigate(Screen.CategoryDetail.createRoute(id))
                },
                onHistoryClick = {
                    navController.navigate(Screen.History.route)
                },
            )
        }

        composable(
            route = Screen.CategoryDetail.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0
            CategoryDetailScreen(
                categoryId = categoryId,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onAddQuestionClick = {
                    navController.navigate(Screen.AddEditQuestion.createRoute(categoryId))
                },
                onEditQuestionClick = { qId ->
                    navController.navigate(Screen.AddEditQuestion.createRoute(categoryId, qId))
                },
                onStartQuizClick = {
                    navController.navigate(Screen.Quiz.createRoute(categoryId))
                }
            )
        }

        composable(
            route = Screen.AddEditQuestion.route,
            arguments = listOf(
                navArgument("categoryId") { type = NavType.IntType },
                navArgument("questionId") { 
                    type = NavType.IntType
                    defaultValue = -1 
                }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0
            val questionId = backStackEntry.arguments?.getInt("questionId").takeIf { it != -1 }
            AddEditQuestionScreen(
                categoryId = categoryId,
                questionId = questionId,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Quiz.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0
            QuizScreen(
                categoryId = categoryId,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onQuizFinish = { score, correct, total, date ->
                    navController.navigate(
                        Screen.QuizResult.createRoute(categoryId, score, correct, total, date)
                    ) {
                        popUpTo(Screen.Quiz.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.QuizResult.route,
            arguments = listOf(
                navArgument("categoryId") { type = NavType.IntType },
                navArgument("score") { type = NavType.IntType },
                navArgument("correct") { type = NavType.IntType },
                navArgument("total") { type = NavType.IntType },
                navArgument("date") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0
            val score = backStackEntry.arguments?.getInt("score") ?: 0
            val correct = backStackEntry.arguments?.getInt("correct") ?: 0
            val total = backStackEntry.arguments?.getInt("total") ?: 0
            val date = backStackEntry.arguments?.getLong("date") ?: 0L
            
            ResultScreen(
                score = score,
                correct = correct,
                total = total,
                date = date,
                onBackToCategory = { 
                    navController.popBackStack(Screen.CategoryDetail.route, inclusive = false) 
                },
                onRetryClick = {
                    navController.navigate(Screen.Quiz.createRoute(categoryId)) {
                        popUpTo(Screen.QuizResult.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.History.route) {
            HistoryScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
