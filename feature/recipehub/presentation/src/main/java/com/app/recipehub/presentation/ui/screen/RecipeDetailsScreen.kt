package com.app.recipehub.presentation.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.app.recipehub.presentation.R
import com.app.recipehub.presentation.ui.component.RecipeDetailsView
import com.app.recipehub.presentation.viewmodel.RecipeListViewModel
import com.app.ui.component.AppEmptyStateView
import com.app.ui.component.AppLayout

/**
 * [RecipeDetailScreen] represent a RecipeDetails Screen that displays the details of a recipe when
 * select a recipe from RecipeHubScreen.
 */
@Composable
fun RecipeDetailScreen(
    recipeId: String,
    viewModel: RecipeListViewModel,
    onBack: () -> Unit
) {
    val recipeDetailsState = viewModel.selectedRecipeDetail.collectAsState()
    LaunchedEffect(
        recipeId,
        viewModel
    ) {
        viewModel.getRecipeById(recipeId)
    }
    AppLayout(
        title = stringResource(R.string.recipehub_recipe_details_screen_title),
        showBackButton = true,
        onBackClicked = onBack
    ) { innerPadding ->
        if (recipeDetailsState.value != null) {
            RecipeDetailsView(
                modifier = Modifier.padding(innerPadding),
                recipe = recipeDetailsState.value!!
            )
        } else {
            val context = LocalContext.current
            AppEmptyStateView(
                title = context.getString(R.string.recipehub_empty_state_screen_title_message),
                message = context.getString(R.string.recipehub_empty_state_screen_description_message),
                icon = Icons.Outlined.Close
            )
        }
    }
}