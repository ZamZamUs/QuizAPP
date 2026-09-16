package com.example.pawal.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object CategoryDetail : Screen("category_detail/{categoryId}") {
        fun createRoute(categoryId: Int) = "category_detail/$categoryId"
    }
    object QuestionList : Screen("question_list/{categoryId}") {
        fun createRoute(categoryId: Int) = "question_list/$categoryId"
    }
    object AddEditQuestion : Screen("add_edit_question/{categoryId}?questionId={questionId}") {
        fun createRoute(categoryId: Int, questionId: Int? = null) = 
            "add_edit_question/$categoryId" + (if (questionId != null) "?questionId=$questionId" else "")
    }
    object Quiz : Screen("quiz/{categoryId}") {
        fun createRoute(categoryId: Int) = "quiz/$categoryId"
    }
    object QuizResult : Screen("quiz_result/{categoryId}/{score}/{correct}/{total}/{date}") {
        fun createRoute(categoryId: Int, score: Int, correct: Int, total: Int, date: Long) = 
            "quiz_result/$categoryId/$score/$correct/$total/$date"
    }
    object History : Screen("history")
}
