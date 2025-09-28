package com.app.recipehub.data.local.source

import com.app.recipehub.data.local.RecipeEntity

interface RecipeLocalDataSource {
    suspend fun insertRecipe(recipe: RecipeEntity)

    suspend fun insertRecipes(recipes: List<RecipeEntity>)

    suspend fun getAllRecipes(): List<RecipeEntity>

    suspend fun getRecipeById(id: String): RecipeEntity?

    suspend fun deleteRecipe(recipe: RecipeEntity)

    suspend fun deleteAllRecipes()
}
