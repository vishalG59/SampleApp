package com.app.recipehub.data.repository

import android.util.Log
import com.app.data.toDomainError
import com.app.domain.DomainError
import com.app.domain.DomainResult
import com.app.network.dispatcher.AppDispatchers
import com.app.network.resource.NetworkResult
import com.app.network.resource.mapThrowableToErrorResult
import com.app.recipehub.data.local.RecipeDao
import com.app.recipehub.data.mapper.toDomainModelList
import com.app.recipehub.data.mapper.toEntity
import com.app.recipehub.data.mapper.toRecipe
import com.app.recipehub.data.remote.source.RecipeRemoteDataSource
import com.app.recipehub.domain.model.Recipe
import com.app.recipehub.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

/**
 * [RecipeRepositoryImpl] provide concrete implementation of [RecipeRepository] contract to access data layer.
 *
 * This class uses [RecipeRemoteDataSource] and [AppDispatchers] to fetch recipes from the remote API and
 * wraps the result using the [DomainResult] type to handle success and error cases efficiently.
 */
class RecipeRepositoryImpl @Inject constructor(
    private val remoteDataSource: RecipeRemoteDataSource,
    private val recipeDao: RecipeDao,
    private val appDispatchers: AppDispatchers
) : RecipeRepository {
    override fun getAllRecipes(): Flow<DomainResult<List<Recipe>, DomainError>> = flow {
        emit(DomainResult.Loading)
        when (val result = remoteDataSource.fetchRecipes()) {
            is NetworkResult.Success -> {
                val domainRecipeList = result.data.toDomainModelList()
                recipeDao.insertRecipes(domainRecipeList.map { it.toEntity() })
                emit(DomainResult.Success(domainRecipeList))
            }

            is NetworkResult.Error -> {
                val domainError = result.toDomainError()
                val localRecipeList = recipeDao.getAllRecipes()
                Log.e("TEST_TAG", "Local List Size = "+localRecipeList.size)
                Log.e("TEST_TAG", "Local List Size = ${localRecipeList}")
                if (localRecipeList.isNotEmpty()) {
                    emit(
                        DomainResult.Failure(
                            error = domainError,
                            data = localRecipeList.map { it.toRecipe() })
                    )
                } else {
                    emit(DomainResult.Failure(domainError))
                }
            }
        }
    }.catch {
        Timber.e(it)
        emit(
            DomainResult.Failure(
                mapThrowableToErrorResult(it)
                    .toDomainError()
            )
        )
    }.flowOn(appDispatchers.io())
}