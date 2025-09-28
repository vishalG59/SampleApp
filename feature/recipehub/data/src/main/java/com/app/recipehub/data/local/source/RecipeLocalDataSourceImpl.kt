package com.app.recipehub.data.local.source

import com.app.recipehub.data.local.RecipeDao
import com.app.recipehub.data.local.RecipeEntity
import javax.inject.Inject

class RecipeLocalDataSourceImpl @Inject constructor(private val recipeDao: RecipeDao) :
    RecipeLocalDataSource {
    override suspend fun insertRecipe(recipe: RecipeEntity) = recipeDao.insertRecipe(recipe)

    override suspend fun insertRecipes(recipes: List<RecipeEntity>) =
        recipeDao.insertRecipes(recipes)

    override suspend fun getAllRecipes(): List<RecipeEntity> = recipeDao.getAllRecipes()


    override suspend fun getRecipeById(id: String): RecipeEntity? = recipeDao.getRecipeById(id)

    override suspend fun deleteRecipe(recipe: RecipeEntity) = recipeDao.deleteRecipe(recipe)

    override suspend fun deleteAllRecipes() = recipeDao.deleteAllRecipes()
}
