package com.example.nineintelligence.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.example.nineintelligence.R
import com.example.nineintelligence.presentation.enter.LoginForm
import com.example.nineintelligence.presentation.enter.RegisterScreen
import com.example.nineintelligence.presentation.home.HomeScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootNavigation() {
    val controller = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = controller,
        route = Graph.ROOT, startDestination = Graph.AUTH
    ) {
        authRoute()
        composable(route = NavigationHolder.HomeScreen.route) {
            HomeScreen()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.authRoute() {
    navigation(startDestination = NavigationHolder.LoginScreen.route, route = Graph.AUTH) {
        composable(route = NavigationHolder.LoginScreen.route, enterTransition = {
            slideIntoContainer(towards = AnimatedContentScope.SlideDirection.Left, tween(800))
        }) {
            LoginForm(type = stringResource(id = R.string.siswa))
        }
        composable(route = NavigationHolder.RegisterScreen.route,
            enterTransition = {
                slideIntoContainer(towards = AnimatedContentScope.SlideDirection.Left, tween(400))
            }) {
            RegisterScreen()
        }
    }
}
