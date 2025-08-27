package com.app.recipehub.presentation.viewmodel

import com.app.domain.DomainError
import com.app.domain.DomainResult
import com.app.recipehub.domain.model.Difficulty
import com.app.recipehub.domain.model.NutritionalValue
import com.app.recipehub.domain.model.Recipe
import com.app.recipehub.domain.usecase.GetRecipesUseCase
import com.app.recipehub.presentation.state.RecipeListUiState
import com.app.ui.utils.ResourceProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RecipeListViewModelTest {

    private lateinit var viewModel: RecipeListViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @MockK
    private lateinit var getRecipesUseCase: GetRecipesUseCase

    @MockK
    private lateinit var resourceProvider: ResourceProvider

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(testDispatcher)

        viewModel = RecipeListViewModel(getRecipesUseCase, resourceProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchRecipes emits Loading and Success`() = runTest {
        val flow = flowOf(
            DomainResult.Loading,
            DomainResult.Success(listOf(mockRecipe))
        )
        coEvery { getRecipesUseCase() } returns flow

        viewModel.fetchRecipes()

        val state = viewModel.recipeDataUiState.value
        assertTrue(state is RecipeListUiState.Success)
        assertEquals(1, (state as RecipeListUiState.Success).recipes.size)
    }

    @Test
    fun `fetchRecipes emits Loading and Error`() = runTest {
        val flow = flowOf(
            DomainResult.Loading,
            DomainResult.Failure(DomainError.NetworkUnavailable)
        )
        coEvery { getRecipesUseCase() } returns flow
        every { resourceProvider.getString(any()) } returns "Network error"

        viewModel.fetchRecipes()

        val state = viewModel.recipeDataUiState.value
        assertTrue(state is RecipeListUiState.Error)
        assertEquals("Network error", (state as RecipeListUiState.Error).message)
    }

    @Test
    fun `getRecipeById returns Recipe for given recipeID from fetched RecipeList`() = runTest {
        val recipeList = listOf(mockRecipe)
        val flow = flowOf(
            DomainResult.Loading,
            DomainResult.Success(recipeList)
        )
        coEvery { getRecipesUseCase() } returns flow

        viewModel.fetchRecipes()
        viewModel.getRecipeById("1")

        assertEquals(mockRecipe, viewModel.selectedRecipeDetail.value)
    }

    companion object {
        val mockRecipe = Recipe(
            id = "1",
            name = "Delicious Pasta Carbonara",
            headline = "A classic Italian pasta dish made with eggs, cheese, pancetta, and pepper.",
            description = "This creamy pasta carbonara is a quick and easy weeknight meal that's full of flavor.",
            imageUrl = "https://via.placeholder.com/600x400.png?text=Pasta+Carbonara",
            thumbnailUrl = "",
            calories = NutritionalValue(550f, "kcal"),
            carbohydrates = NutritionalValue(60f, "g"),
            fats = NutritionalValue(25f, "g"),
            proteins = NutritionalValue(20f, "g"),
            // ... fill other fields as needed for a complete preview
            difficulty = Difficulty.MEDIUM,
            countryCode = "IT",
            favoriteCount = 120,
            ingredients = emptyList(), // Populate if you add ingredients section
            applicableWeeks = emptyList()
        )
    }
}
