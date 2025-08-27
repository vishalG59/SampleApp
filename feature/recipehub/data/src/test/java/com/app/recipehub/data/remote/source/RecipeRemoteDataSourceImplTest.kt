package com.app.recipehub.data.remote.source

import com.app.network.resource.ErrorType
import com.app.network.resource.NetworkResult
import com.app.recipehub.data.remote.api.RecipeApiService
import com.app.recipehub.data.remote.model.RecipeDTO
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RecipeRemoteDataSourceImplTest {
    @MockK
    private lateinit var apiService: RecipeApiService

    @MockK
    private lateinit var firstRecipeDTO: RecipeDTO

    @MockK
    private lateinit var secondRecipeDTO: RecipeDTO

    private lateinit var remoteDataSource: RecipeRemoteDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        remoteDataSource = RecipeRemoteDataSourceImpl(apiService)
    }

    @Test
    fun `fetchRecipes returns success when API call is successful`() = runTest {
        val mockResponse = listOf(
            firstRecipeDTO, secondRecipeDTO
        )

        val retrofitSuccessResponse = Response.success(mockResponse)

        coEvery { apiService.getRecipes() } returns retrofitSuccessResponse

        val result = remoteDataSource.fetchRecipes()

        assert(result is NetworkResult.Success)
        assertEquals(mockResponse, (result as NetworkResult.Success).data)
        coVerify(exactly = 1) { apiService.getRecipes() }
    }

    @Test
    fun `fetchRecipes returns error when API call throws exception`() = runTest {
        val runtimeException = RuntimeException("Network failure")
        coEvery { apiService.getRecipes() } throws runtimeException

        val result = remoteDataSource.fetchRecipes()

        assert(result is NetworkResult.Error)

        val error = result as NetworkResult.Error

        assertEquals(ErrorType.UNKNOWN, error.type)
        assert(error.message.contains("RuntimeException"))
        coVerify(exactly = 1) { apiService.getRecipes() }
    }
}
