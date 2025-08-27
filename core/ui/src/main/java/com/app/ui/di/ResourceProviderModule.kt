package com.app.ui.di

import com.app.ui.utils.AndroidResourceProvider
import com.app.ui.utils.ResourceProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * [ResourceProviderModule] is a DI module that provides a [ResourceProvider] implementation
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class ResourceProviderModule {

    @Binds
    abstract fun bindResourceProvider(
        androidResourceProvider: AndroidResourceProvider
    ): ResourceProvider
}
