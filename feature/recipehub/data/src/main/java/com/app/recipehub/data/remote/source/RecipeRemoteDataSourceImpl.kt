package com.app.recipehub.data.remote.source

import com.app.network.resource.NetworkResult
import com.app.network.resource.safeApiCall
import com.app.recipehub.data.remote.api.RecipeApiService
import com.app.recipehub.data.remote.model.RecipeDTO
import javax.inject.Inject

/**
 * Implementation of [RecipeRemoteDataSource] responsible for fetching recipes from the remote API.
 *
 * This class uses [RecipeApiService] to make network requests and wraps the result
 * using the [NetworkResult] type to handle success and error cases efficiently.
 *
 * @property apiService The Retrofit service interface used to fetch recipe data from the network.
 */
class RecipeRemoteDataSourceImpl @Inject constructor(
    private val apiService: RecipeApiService) : RecipeRemoteDataSource {
    override suspend fun fetchRecipes(): NetworkResult<List<RecipeDTO>> {
        return safeApiCall {
            apiService.getRecipes()
        }
    }
}
