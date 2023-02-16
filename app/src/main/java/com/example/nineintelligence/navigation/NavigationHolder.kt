package com.example.nineintelligence.navigation

sealed class NavigationHolder(val route: String) {
    object BoardingScreen : NavigationHolder("boarding_screen")
    object HomeScreen : NavigationHolder("home_screen")
    object RegisterScreen : NavigationHolder("register_screen")
    object LoginScreen : NavigationHolder("login_screen")

    object HomeScreenChild: NavigationHolder("home_screen_child")
    object ProfileScreenChild: NavigationHolder("profile_screen_child")
}

object Graph{
    const val ROOT = "root_nav"
    const val BOARDING = "boarding_nav"
    const val AUTH = "auth_nav"
    const val HOME = "home_nav"
}
