package com.app.recipehub.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.app.recipehub.presentation.ui.screen.RecipeDetailScreen
import com.app.recipehub.presentation.ui.screen.RecipeHubScreen
import com.app.recipehub.presentation.viewmodel.RecipeListViewModel

fun NavGraphBuilder.recipeHubNavGraph(
    navController: NavHostController,
    getRecipeListViewModel: @Composable (NavBackStackEntry) -> RecipeListViewModel = { backStackEntry ->
        hiltViewModel(backStackEntry)
    }
) {
    navigation(
        startDestination = RecipeHubFeatureRoutes.Screen.RecipeHub.path,
        route = RecipeHubFeatureRoutes.path
    ) {
        composable(RecipeHubFeatureRoutes.Screen.RecipeHub.path) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(RecipeHubFeatureRoutes.path)
            }
            val viewModel: RecipeListViewModel = hiltViewModel(parentEntry)
            RecipeHubScreen(getRecipeListViewModel(parentEntry)) { recipeId ->
                navController.navigate(RecipeHubFeatureRoutes.Screen.RecipeDetail.createRoute(recipeId))
            }
        }

        val recipeDetail =
            RecipeHubFeatureRoutes.Screen.RecipeDetail
        composable(
            route = recipeDetail.path,
            arguments = recipeDetail.navArguments
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(RecipeHubFeatureRoutes.path)
            }
            val viewModel: RecipeListViewModel = getRecipeListViewModel(parentEntry)
            val itemId =
                backStackEntry.arguments?.getString(RecipeHubFeatureRoutes.Screen.RecipeDetail.argRecipeId)
            requireNotNull(itemId) { "itemId argument is required for RecipeDetail Screen" }

            RecipeDetailScreen(itemId, viewModel, onBack = {
                navController.popBackStack()
            })
        }
    }
}


