package com.app.recipehub.presentation.state

import com.app.recipehub.domain.model.Recipe

/**
 * [RecipeListUiState] is a sealed interface that represents the different states of the RecipeHubScreen UI.
 */
sealed interface RecipeListUiState {
    object Loading : RecipeListUiState
    data class Success(val recipes: List<Recipe>) : RecipeListUiState
    data class Error(val message: String) : RecipeListUiState
    object Empty : RecipeListUiState

    data class SuccessWithError(val recipes: List<Recipe>, val errorMessage: String) :
        RecipeListUiState
}