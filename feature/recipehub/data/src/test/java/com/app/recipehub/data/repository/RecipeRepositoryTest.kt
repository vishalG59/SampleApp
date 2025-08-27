package com.app.recipehub.data.repository

import com.app.data.toDomainError
import com.app.domain.DomainResult
import com.app.network.dispatcher.AppDispatchers
import com.app.network.resource.ErrorType
import com.app.network.resource.NetworkResult
import com.app.network.resource.mapThrowableToErrorResult
import com.app.recipehub.data.mapper.toDomainModelList
import com.app.recipehub.data.remote.model.RecipeDTO
import com.app.recipehub.data.remote.source.RecipeRemoteDataSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.ConnectException

class RecipeRepositoryImplTest {
    private val dispatcher = UnconfinedTestDispatcher()

    @MockK
    private lateinit var remoteDataSource: RecipeRemoteDataSource

    @MockK
    private lateinit var appDispatchers: AppDispatchers

    private lateinit var repository: RecipeRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        every { appDispatchers.io() } returns dispatcher
        repository = RecipeRepositoryImpl(remoteDataSource, appDispatchers)
    }

    @Test
    fun `getAllRecipes emits Loading and Success when remoteDataSource gives Success returns Success result`() =
        runTest {
            val apiList = listOf(recipeDTO)
            val domainList = apiList.toDomainModelList()

            coEvery { remoteDataSource.fetchRecipes() } returns NetworkResult.Success(apiList)

            val results = repository.getAllRecipes().toList()

            assertEquals(2, results.size)
            assertEquals(DomainResult.Loading, results[0])
            assertEquals(DomainResult.Success(domainList), results[1])
        }

    @Test
    fun `getAllRecipes emits Loading and Failure when remoteDataSource gives Network Error returns expected Error`() = runTest {
        val errorType = NetworkResult.Error(type = ErrorType.INTERNAL_SERVER_ERROR, message = "Server error")
        val domainError =errorType.toDomainError()

        coEvery { remoteDataSource.fetchRecipes() } returns errorType

        val results = repository.getAllRecipes().toList()

        assertEquals(2, results.size)
        assertEquals(DomainResult.Loading, results[0])
        assertEquals(DomainResult.Failure(domainError), results[1])
    }

    @Test
    fun `getAllRecipes emits Loading and Failed with exception returns expected DomainError`() = runTest {
        val exception = ConnectException("Connection Refused")
        val domainError = DomainResult.Failure(
            mapThrowableToErrorResult(exception)
                .toDomainError()
        )
        coEvery { remoteDataSource.fetchRecipes() } throws exception

        val results = repository.getAllRecipes().toList()

        assertEquals(2, results.size)
        assertEquals(DomainResult.Loading, results[0])
        assertEquals(domainError, results[1])
    }

    companion object {
        val recipeDTO = RecipeDTO(
            id = "1",
            name = "Spaghetti Bolognese",
            headline = "Classic Italian Pasta",
            description = "A traditional Italian pasta dish made with minced meat and tomato sauce.",
            calories = "550 kcal",
            carbos = "60 g",
            fats = "20 g",
            proteins = "30 g",
            image = "https://example.com/images/spaghetti.jpg",
            thumb = "https://example.com/images/spaghetti_thumb.jpg",
            difficulty = 1,
            time = "30 min",
            country = "Italy",
            favorites = 150,
            deliverableIngredients = listOf("Pasta", "Ground Beef", "Tomato Sauce"),
            undeliverableIngredients = listOf("Parmesan Cheese"),
            weeks = listOf("2025-W35", "2025-W36")
        )

    }
}

