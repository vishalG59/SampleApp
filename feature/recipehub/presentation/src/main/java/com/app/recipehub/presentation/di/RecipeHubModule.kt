package com.app.recipehub.presentation.di

import com.app.recipehub.domain.repository.RecipeRepository
import com.app.recipehub.domain.usecase.GetRecipesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RecipeHubModule {

    @Provides
    fun provideRecipeUseCase(repository: RecipeRepository): GetRecipesUseCase {
        return GetRecipesUseCase(repository)
    }
}