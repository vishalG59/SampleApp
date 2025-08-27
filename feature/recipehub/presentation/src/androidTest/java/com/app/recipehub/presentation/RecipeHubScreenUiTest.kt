package com.app.recipehub.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.app.recipehub.presentation.state.RecipeListUiState
import com.app.recipehub.presentation.ui.screen.RecipeHubScreen
import com.app.recipehub.presentation.utils.getMockRecipeList
import com.app.recipehub.presentation.viewmodel.RecipeListViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class RecipeHubScreenUiTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun recipeListScreen_shouldDisplaysRecipesList_whenRecipeListIsNotEmpty() {
        val mockRecipeList = getMockRecipeList(mockRecipeListJsonFile)
        val fakeState = MutableStateFlow<RecipeListUiState>(
            RecipeListUiState.Success(recipes = mockRecipeList)
        )
        val mockViewModel = mockk<RecipeListViewModel>(relaxed = true).apply {
            every { recipeDataUiState } returns fakeState
        }
        composeTestRule.setContent {
            RecipeHubScreen(viewModel = mockViewModel) {
                //No-op
            }
        }

        composeTestRule
            .onNodeWithText("Crispy Fish Goujons")
            .assertIsDisplayed()
    }

    @Test
    fun recipeListScreen_performScrollAndClick() {
        val mockRecipeList = getMockRecipeList(mockRecipeListJsonFile)

        val fakeState = MutableStateFlow<RecipeListUiState>(
            RecipeListUiState.Success(recipes = mockRecipeList)
        )
        val mockViewModel = mockk<RecipeListViewModel>(relaxed = true).apply {
            every { recipeDataUiState } returns fakeState
        }
        composeTestRule.setContent {
            RecipeHubScreen(viewModel = mockViewModel) {
                //No-op
            }
        }
        composeTestRule
            .onNodeWithText("Crispy Fish Goujons")
            .performScrollTo()
            .assertIsDisplayed()
            .performClick()
    }

    @Test
    fun recipeListScreen_shouldDisplayFullPageError_whenRecipeListIsEmpty() {
        val fakeState = MutableStateFlow<RecipeListUiState>(
            RecipeListUiState.Empty
        )
        val mockViewModel = mockk<RecipeListViewModel>(relaxed = true).apply {
            every { recipeDataUiState } returns fakeState
        }
        composeTestRule.setContent {
            RecipeHubScreen(viewModel = mockViewModel) {
                //No-op
            }
        }
        composeTestRule
            .onNodeWithText("No Recipes Found!")
            .assertIsDisplayed()
    }

    @Test
    fun recipeListScreen_shouldDisplayErrorBanner_whenErrorOccurred() {
        val fakeState = MutableStateFlow<RecipeListUiState>(
            RecipeListUiState.Error("Internet not available")
        )
        val mockViewModel = mockk<RecipeListViewModel>(relaxed = true).apply {
            every { recipeDataUiState } returns fakeState
        }
        composeTestRule.setContent {
            RecipeHubScreen(viewModel = mockViewModel) {
                //No-op
            }
        }
        composeTestRule
            .onNodeWithText("Internet not available")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Retry")
            .assertIsDisplayed()
            .performClick()
            .assertIsEnabled()
    }

    @Test
    fun recipeListScreen_shouldDisplayLoadingIndicator_whenLoadingState() {
        val fakeState = MutableStateFlow<RecipeListUiState>(
            RecipeListUiState.Loading
        )
        val mockViewModel = mockk<RecipeListViewModel>(relaxed = true).apply {
            every { recipeDataUiState } returns fakeState
        }
        composeTestRule.setContent {
            RecipeHubScreen(viewModel = mockViewModel) {
                //No-op
            }
        }
        composeTestRule
            .onNodeWithTag("progressIndicator")
            .assertIsDisplayed()
    }

    companion object {
        const val mockRecipeListJsonFile = "recipe_list_mock.json"
    }
}
