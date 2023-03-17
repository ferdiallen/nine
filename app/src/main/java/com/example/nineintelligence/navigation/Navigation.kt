package com.example.nineintelligence.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.nineintelligence.presentation.enter.LoginForm
import com.example.nineintelligence.presentation.enter.RegisterScreen
import com.example.nineintelligence.presentation.home.HomeScreen
import com.example.nineintelligence.ui.theme.MainBlueColor
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootNavigation(
    viewModel: NavigationViewModel = koinViewModel()
) {
    val controller = rememberAnimatedNavController()
    val sysUi = rememberSystemUiController()
    val currentStack by controller.currentBackStackEntryAsState()
    val isLogin by viewModel.hasLoggedIn.collectAsStateWithLifecycle()
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
    LaunchedEffect(key1 = isLogin, block = {
        if (isLogin) {
            controller.navigate(NavigationHolder.HomeScreen.route) {
                popUpTo(NavigationHolder.LoginScreen.route) {
                    inclusive = true
                }
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
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left, tween(500))
            }) {
            HomeScreen(systemUi = sysUi)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.authRoute(controller: NavController) {
    navigation(
        startDestination = NavigationHolder.LoginScreen.route,
        route = Graph.AUTH
    ) {
        composable(route = NavigationHolder.LoginScreen.route, enterTransition = {
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
        ) {
            LoginForm(
                controller = controller
            )
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
    }
}
