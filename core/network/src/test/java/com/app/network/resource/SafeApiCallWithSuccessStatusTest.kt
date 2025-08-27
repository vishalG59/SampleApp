package com.app.network.resource
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class SafeApiCallWithSuccessStatusTest {

    private val testDispatcher = StandardTestDispatcher()

    data class DummyData(val name: String)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic("com.app.network.resource.NetworkResourceKt") // If handleHttpError / mapThrowableToErrorResult are top-level
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `safeApiCall returns Success when response is successful and body is not null`() = runTest {
        val expectedBody = DummyData("Test")
        val mockResponse = Response.success(expectedBody)
        
        val result = safeApiCall(dispatcher = testDispatcher) {
            mockResponse
        }

        assertTrue(result is NetworkResult.Success)
        assertEquals(expectedBody, (result as NetworkResult.Success).data)
    }

    @Test
    fun `safeApiCall returns Error when response is successful but body is null`() = runTest {
        val mockResponse: Response<DummyData> = Response.success(null)

        val result = safeApiCall<DummyData>(dispatcher = testDispatcher) {
            mockResponse
        }

        assertTrue(result is NetworkResult.Error)
        assertEquals(ErrorType.EMPTY_RESPONSE, (result as NetworkResult.Error).type)
    }
}
