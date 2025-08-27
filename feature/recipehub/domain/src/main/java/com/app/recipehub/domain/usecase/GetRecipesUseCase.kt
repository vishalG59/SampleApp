package com.app.recipehub.domain.usecase

import com.app.domain.DomainError
import com.app.domain.DomainResult
import com.app.recipehub.domain.model.Recipe
import com.app.recipehub.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case for fetching the list of all recipes.
 */
class GetRecipesUseCase(
    private val recipeRepository: RecipeRepository,
) {
    /**
     * invoke will executes the use case.
     *
     * @return A Flow emitting the result of the recipe fetching operation.
     */
    operator fun invoke(): Flow<DomainResult<List<Recipe>, DomainError>> {
        return recipeRepository.getAllRecipes()
    }
}