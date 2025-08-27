package com.app.recipehub.data.remote.api

import com.app.recipehub.data.remote.model.RecipeDTO
import retrofit2.Response
import retrofit2.http.GET

/**
 * [RecipeApiService] provides API client for Recipe APIs.
 */
interface RecipeApiService {
    @GET(RecipeApi.getAllRecipes)
    suspend fun getRecipes(): Response<List<RecipeDTO>>
}

object RecipeApi {
    const val getAllRecipes = "android-test/recipes.json"
}