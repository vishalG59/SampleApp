package com.app.recipehub.data.di

import com.app.network.dispatcher.AppDispatchers
import com.app.recipehub.data.remote.api.RecipeApiService
import com.app.recipehub.data.remote.source.RecipeRemoteDataSource
import com.app.recipehub.data.remote.source.RecipeRemoteDataSourceImpl
import com.app.recipehub.data.repository.RecipeRepositoryImpl
import com.app.recipehub.domain.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * [RecipeDataModule] is a DI module that provides a [RecipeRepository] implementation
 */
@Module
@InstallIn(SingletonComponent::class)
object RecipeDataModule {
    @Provides
    @Singleton
    fun provideRecipeApiService(retrofit: Retrofit): RecipeApiService {
        return retrofit.create(RecipeApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        recipeApiService: RecipeApiService
    ): RecipeRemoteDataSource {
        return RecipeRemoteDataSourceImpl(recipeApiService)
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(
        remoteDataSource: RecipeRemoteDataSource,
        dispatchers: AppDispatchers
    ): RecipeRepository {
        return RecipeRepositoryImpl(remoteDataSource, dispatchers)
    }
}