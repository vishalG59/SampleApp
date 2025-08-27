package com.app.recipehub.data.remote.source

import com.app.network.resource.NetworkResult
import com.app.recipehub.data.remote.model.RecipeDTO

/**
 * [RecipeRemoteDataSource] interface for fetching recipes from a remote data source.
 * It provides abstraction for access RemoteDataSource from Repository.
 */
interface RecipeRemoteDataSource {
     suspend fun fetchRecipes(): NetworkResult<List<RecipeDTO>>
}