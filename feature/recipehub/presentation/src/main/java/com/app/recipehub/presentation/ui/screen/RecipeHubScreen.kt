package com.app.recipehub.presentation.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.recipehub.domain.model.Difficulty
import com.app.recipehub.domain.model.NutritionalValue
import com.app.recipehub.domain.model.Recipe
import com.app.recipehub.presentation.R
import com.app.recipehub.presentation.state.RecipeListUiState
import com.app.ui.component.AppEmptyStateView
import com.app.recipehub.presentation.ui.component.RecipeCardView
import com.app.recipehub.presentation.viewmodel.RecipeListViewModel
import com.app.ui.component.AppCircularProgressIndicator
import com.app.ui.component.AppErrorBanner
import com.app.ui.component.AppLayout

/**
 * [RecipeHubScreen] displays a list of recipes.
 */
@Composable
fun RecipeHubScreen(viewModel: RecipeListViewModel, onItemClick: (String) -> Unit) {
    val recipes = viewModel.recipeDataUiState.collectAsState()
    AppLayout(
        title = stringResource(R.string.recipehub_recipelist_screen_title),
        showBackButton = false,
    ) { innerPadding ->
        when (recipes.value) {
            is RecipeListUiState.Success -> {
                RecipeListView(
                    modifier = Modifier.padding(innerPadding),
                    recipes = (recipes.value as RecipeListUiState.Success).recipes,
                    onItemClick = onItemClick
                )
            }

            is RecipeListUiState.Error -> {
                AppErrorBanner(
                    modifier = Modifier.padding(innerPadding),
                    message = (recipes.value as RecipeListUiState.Error).message,
                    onRetry = {
                        viewModel.fetchRecipes()
                    }
                )
            }

            is RecipeListUiState.Loading -> {
                AppCircularProgressIndicator(isLoading = true)
            }

            is RecipeListUiState.Empty -> {
                AppEmptyStateView(
                    title = stringResource(R.string.recipehub_empty_state_screen_title_message),
                    message = stringResource(R.string.recipehub_empty_state_screen_description_message),
                    icon = Icons.Outlined.Close
                )
            }
        }
    }
}


@Composable
fun RecipeListView(
    modifier: Modifier = Modifier,
    recipes: List<Recipe>,
    onItemClick: (String) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(recipes) { recipe ->
            RecipeCardView(
                recipe = recipe,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .clickable { onItemClick(recipe.id) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeListPreview() {
    MaterialTheme {
        val previewRecipe = Recipe(
            id = "1",
            name = "Delicious Pasta Carbonara",
            headline = "A classic Italian pasta dish made with eggs, cheese, pancetta, and pepper.",
            description = "This creamy pasta carbonara is a quick and easy weeknight meal that's full of flavor. The key to a perfect carbonara is to work quickly and use high-quality ingredients. Don't be intimidated by the raw egg; the heat from the pasta cooks it to create a velvety sauce. Ensure your pancetta is crispy and the Pecorino Romano cheese is freshly grated for the best results. This recipe is a family favorite and is sure to impress your guests with its authentic taste and texture.",
            imageUrl = "https://img.hellofresh.com/f_auto,q_auto/hellofresh_s3/image/533143aaff604d567f8b4571.jpg", // Placeholder image URL
            thumbnailUrl = "",
            calories = NutritionalValue(550f, "kcal"),
            carbohydrates = NutritionalValue(60f, "g"),
            fats = NutritionalValue(25f, "g"),
            proteins = NutritionalValue(20f, "g"),
            // ... fill other fields as needed for a complete preview
            difficulty = Difficulty.MEDIUM,
            countryCode = "IT",
            favoriteCount = 120,
            ingredients = emptyList(),
            applicableWeeks = emptyList()
        )
        val recipeList = listOf(previewRecipe, previewRecipe, previewRecipe)
        RecipeListView(recipes = recipeList) {

        }
    }
}