package com.app.recipehub.presentation.utils

import androidx.test.platform.app.InstrumentationRegistry
import com.app.recipehub.data.mapper.toDomainModel
import com.app.recipehub.data.mapper.toDomainModelList
import com.app.recipehub.data.remote.model.RecipeDTO
import com.app.recipehub.domain.model.Recipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun getMockRecipeList(recipeListFileName: String): List<Recipe> {
    val json = loadJsonFromAsset(recipeListFileName)
    return parseRecipesFromJson(json)
}

private fun loadJsonFromAsset(fileName: String): String {
    val context = InstrumentationRegistry.getInstrumentation().context
    return context.assets.open(fileName).bufferedReader().use { it.readText() }
}

fun getMockRecipe(recipeFileName: String): Recipe {
    val json = loadJsonFromAsset(recipeFileName)
    return parseSingleRecipeFromJson(json)
}

private fun parseSingleRecipeFromJson(json: String): Recipe {
    val listType = object : TypeToken<RecipeDTO>() {}.type
    val recipeDTO: RecipeDTO = Gson().fromJson(json, listType)
    return recipeDTO.toDomainModel()
}

private fun parseRecipesFromJson(json: String): List<Recipe> {
    val listType = object : TypeToken<List<RecipeDTO>>() {}.type
    val recipeDtoList: List<RecipeDTO> = Gson().fromJson(json, listType)
    return recipeDtoList.toDomainModelList()
}
