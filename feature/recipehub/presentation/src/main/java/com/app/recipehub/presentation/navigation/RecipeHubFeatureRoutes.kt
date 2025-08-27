package com.app.recipehub.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.app.ui.navigation.AppRoute
import com.app.ui.navigation.FeatureRoute

object RecipeHubFeatureRoutes : FeatureRoute {
    override val path: String = "recipehub_graph"

    override val startDestination: AppRoute = Screen.RecipeHub

    override val routes: List<AppRoute> = listOf(
        Screen.RecipeHub,
        Screen.RecipeDetail
    )

    sealed class Screen(override val path: String) : AppRoute {

        data object RecipeHub : Screen("recipehub")

        data object RecipeDetail : Screen("recipehub/detail/{recipeId}") {
            const val argRecipeId = "recipeId"

            val navArguments: List<NamedNavArgument> = listOf(
                navArgument(argRecipeId) { type = NavType.StringType }
            )

            fun createRoute(recipeId: String): String = "recipehub/detail/$recipeId"
        }
    }
}