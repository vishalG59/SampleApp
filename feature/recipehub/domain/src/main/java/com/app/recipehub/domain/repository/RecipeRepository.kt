package com.app.recipehub.domain.repository

import com.app.domain.DomainError
import com.app.domain.DomainResult
import com.app.recipehub.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for accessing recipe from data layer.
 *
 * This interface defines the contract for operations to fetch recipes,
 * abstracting the data source from the rest of the application(ViewModels or UseCases).
 */
interface RecipeRepository {
     fun getAllRecipes():  Flow<DomainResult<List<Recipe>, DomainError>>
}