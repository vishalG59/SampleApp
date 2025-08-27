package com.app.recipehub.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.app.recipehub.presentation.utils.getMockRecipe
import com.app.recipehub.presentation.ui.screen.RecipeDetailScreen
import com.app.recipehub.presentation.viewmodel.RecipeListViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class RecipeDetailScreenUiTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun recipeDetailsScreen_shouldDisplaysRecipesDetail_whenProvidedCorrectRecipeId() {
        val mockRecipe = getMockRecipe(mockRecipeJsonFile)
        val mockRecipeId = mockRecipe.id
        val fakeRecipeState = MutableStateFlow(mockRecipe)
        val mockViewModel = mockk<RecipeListViewModel>(relaxed = true).apply {
            every { selectedRecipeDetail } returns fakeRecipeState
        }
        composeTestRule.setContent {
            RecipeDetailScreen(recipeId = mockRecipeId, viewModel = mockViewModel) {
                //No-op
            }
        }
        composeTestRule
            .onNodeWithText("Recipe Details")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("Crispy Fish Goujons")
            .assertIsDisplayed()
    }

    companion object {
        const val mockRecipeJsonFile = "recipe_mock.json"
    }
}