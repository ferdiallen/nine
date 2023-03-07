package com.example.nineintelligence.navigation

sealed class NavigationHolder(val route: String) {
    object HomeScreen : NavigationHolder("home_screen")
    object RegisterScreen : NavigationHolder("register_screen")
    object LoginScreen : NavigationHolder("login_screen")

    object HomeScreenChild : NavigationHolder("home_screen_child")
    object ProfileScreenChild : NavigationHolder("profile_screen_child")
    object BankSoalScreen : NavigationHolder("bank_soal_scren")

    object ExamScreen : NavigationHolder("exam_screen")
    object DiscussionScreen : NavigationHolder("discussion_screen")
    object QuestionDiscussion : NavigationHolder("question_discussion_screen")
}

object Graph {
    const val ROOT = "root_nav"
    const val AUTH = "auth_nav"
}
