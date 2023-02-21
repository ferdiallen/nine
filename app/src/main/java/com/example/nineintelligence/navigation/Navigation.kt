package com.example.nineintelligence.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.nineintelligence.R
import com.example.nineintelligence.presentation.boarding.MainBoardingScreen
import com.example.nineintelligence.presentation.enter.LoginForm
import com.example.nineintelligence.presentation.enter.RegisterScreen
import com.example.nineintelligence.presentation.home.HomeScreen
import com.example.nineintelligence.ui.theme.MainBlueColor
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootNavigation() {
    val controller = rememberAnimatedNavController()
    val sysUi = rememberSystemUiController()
    val currentStack by controller.currentBackStackEntryAsState()
    LaunchedEffect(key1 = currentStack, block = {
        when (currentStack?.destination?.route) {
            NavigationHolder.LoginScreen.route -> {
                sysUi.setStatusBarColor(MainBlueColor, darkIcons = false)
            }

            NavigationHolder.RegisterScreen.route -> {
                sysUi.setStatusBarColor(Color.White, darkIcons = true)
            }
        }
    })
    AnimatedNavHost(
        navController = controller,
        route = Graph.ROOT, startDestination = Graph.AUTH
    ) {
        authRoute(controller)
        composable(route = NavigationHolder.HomeScreen.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left, tween(300))
            }) {
            HomeScreen(systemUi = sysUi)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.authRoute(controller: NavController) {
    navigation(
        startDestination = NavigationHolder.BoardingScreen.route,
        route = Graph.AUTH
    ) {
        composable(route = NavigationHolder.LoginScreen.route+ "/{type}", enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentScope.SlideDirection.Left,
                tween(300)
            )
        }, exitTransition = {
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Up, tween(300))
        }, popEnterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Down, tween(300))
        }, arguments = listOf(navArgument(
            "type"
        ) {
            type = NavType.StringType
            defaultValue = ""
        })
        ) { out ->
            out.arguments?.let { argument ->
                LoginForm(
                    type = argument.getString("type", ""),
                    controller = controller
                )
            }
        }
        composable(route = NavigationHolder.RegisterScreen.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Up,
                    tween(300)
                )
            }, popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, tween(300))
            }, exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Up, tween(300))
            }, popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Down, tween(300))
            }) {
            RegisterScreen(controller = controller)
        }
        composable(route = NavigationHolder.BoardingScreen.route) {
            MainBoardingScreen(controller)
        }
    }
}
