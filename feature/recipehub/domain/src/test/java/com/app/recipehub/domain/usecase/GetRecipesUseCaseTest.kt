package com.app.recipehub.domain.usecase

import com.app.domain.DomainResult
import com.app.recipehub.domain.model.Recipe
import com.app.recipehub.domain.repository.RecipeRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetRecipesUseCaseTest {
    @MockK
    lateinit var repository: RecipeRepository

    @MockK
    lateinit var recipe: Recipe

    lateinit var getRecipesUseCase: GetRecipesUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        getRecipesUseCase = GetRecipesUseCase(repository)
    }

    @Test
    fun `test GetRecipesUseCase when return Success Result`() = runTest {
        val flow = flow {
            emit(DomainResult.Success(listOf<Recipe>(recipe)))
        }

        every { repository.getAllRecipes() } returns flow

         val result = getRecipesUseCase.invoke().toList()

        assertTrue(result[0] is DomainResult.Success)
        assertEquals(1,(result[0] as DomainResult.Success).data.size )
    }
}
