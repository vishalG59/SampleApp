package com.app.recipehub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.domain.DomainResult
import com.app.recipehub.domain.model.Recipe
import com.app.recipehub.domain.usecase.GetRecipesUseCase
import com.app.recipehub.presentation.R
import com.app.recipehub.presentation.mapper.toDisplayMessageResourceId
import com.app.recipehub.presentation.state.RecipeListUiState
import com.app.ui.utils.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {
    private val _recipeDataUiState = MutableStateFlow<RecipeListUiState>(RecipeListUiState.Loading)
    val recipeDataUiState: StateFlow<RecipeListUiState> = _recipeDataUiState.asStateFlow()

    private val _selectedRecipeDetail = MutableStateFlow<Recipe?>(null)
    val selectedRecipeDetail: StateFlow<Recipe?> = _selectedRecipeDetail.asStateFlow()

    init {
        fetchRecipes()
    }

    fun fetchRecipes() {
        getRecipesUseCase()
            .onEach { domainResult ->
                when (domainResult) {
                    is DomainResult.Loading -> _recipeDataUiState.value = RecipeListUiState.Loading
                    is DomainResult.Success -> {
                        if (domainResult.data.isEmpty())
                            _recipeDataUiState.value = RecipeListUiState.Empty
                        else
                            _recipeDataUiState.value = RecipeListUiState.Success(domainResult.data)
                    }

                    is DomainResult.Failure -> {
                        if (domainResult.data != null) {
                            _recipeDataUiState.value = RecipeListUiState.SuccessWithError(
                                recipes = domainResult.data ?: emptyList(),
                                errorMessage = resourceProvider.getString(domainResult.error.toDisplayMessageResourceId())
                            )
                        } else {
                            _recipeDataUiState.value = RecipeListUiState.Error(
                                resourceProvider.getString(domainResult.error.toDisplayMessageResourceId())
                            )
                        }
                    }
                }
            }.catch { throwable ->
                Timber.e(throwable, "Exception in fetchRecipes flow")
                val errorMessage = throwable.localizedMessage
                    ?: resourceProvider.getString(R.string.recipehub_error_unknown_failure)
                _recipeDataUiState.value = RecipeListUiState.Error(
                    resourceProvider.getString(
                        R.string.recipehub_error_unexpected_exception_message,
                        errorMessage
                    )
                )
            }.launchIn(viewModelScope)
    }

    /**
     * Selects a recipe by its ID from the currently available recipes for RecipeDetailScreen.
     */
    fun getRecipeById(recipeId: String) {
        val currentState = _recipeDataUiState.value
        val recipeList = if (currentState is RecipeListUiState.Success) {
            currentState.recipes
        } else {
            null
        }
        _selectedRecipeDetail.value = recipeList?.find { it.id == recipeId }
    }
}


