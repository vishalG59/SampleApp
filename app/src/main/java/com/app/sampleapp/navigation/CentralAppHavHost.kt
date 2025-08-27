package com.app.sampleapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.recipehub.presentation.navigation.RecipeHubFeatureRoutes
import com.app.recipehub.presentation.navigation.recipeHubNavGraph
import com.app.sampleapp.screen.SplashScreen
import com.app.ui.navigation.AppRoute

@Composable
fun CentralAppHavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    NavHost(
        modifier = modifier,
        startDestination = MainApp.Splash.path,
        navController = navHostController
    ) {
        composable(route = MainApp.Splash.path) {
            SplashScreen {
                navHostController.navigate(RecipeHubFeatureRoutes.path) {
                    popUpTo(MainApp.Splash.path) {
                        inclusive = true
                    }
                }
            }
        }

        recipeHubNavGraph(navController = navHostController)
    }
}

sealed class MainApp(override val path: String) : AppRoute {
    object Splash : MainApp("splash")
}