package com.app.network.resource

import com.app.network.constants.NetworkErrorMessages
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import retrofit2.HttpException
import retrofit2.Response

@RunWith(Parameterized::class)
class SafeApiCallWithHttpErrorTest(
    private val exception: Throwable,
    private val expectedError: NetworkResult.Error
) {

    companion object {

        @JvmStatic
        @Parameterized.Parameters(name = "{index}: Exception = {0}")
        fun data(): Collection<Array<Any>> {
            val dummyResponseBody = object : ResponseBody() {
                override fun contentLength(): Long = 0
                override fun contentType() = null
                override fun source() = throw UnsupportedOperationException()
            }

            val httpException401 =
                object : HttpException(Response.error<String>(401, dummyResponseBody)) {
                    override fun code() = 401
                    override fun message() = "Unauthorized"
                }

            val httpException403 =
                object : HttpException(Response.error<String>(403, dummyResponseBody)) {
                    override fun code() = 403
                    override fun message() = "Forbidden"
                }

            val httpException500 =
                object : HttpException(Response.error<String>(500, dummyResponseBody)) {
                    override fun code() = 500
                    override fun message() = "Internal Server Error"
                }

            return listOf(
                arrayOf(
                    httpException401,
                    NetworkResult.Error(
                        type = ErrorType.UNAUTHORIZED,
                        message = NetworkErrorMessages.format(
                            httpException401,
                            NetworkErrorMessages.httpError(401, httpException401.message())
                        ),
                        exception = httpException401,
                        httpStatusCode = 401,
                        rawErrorBody = null
                    )
                ),
                arrayOf(
                    httpException403,
                    NetworkResult.Error(
                        type = ErrorType.FORBIDDEN,
                        message = NetworkErrorMessages.format(
                            httpException403,
                            NetworkErrorMessages.httpError(403, httpException403.message())
                        ),
                        exception = httpException403,
                        httpStatusCode = 403,
                        rawErrorBody = null
                    )
                ),
                arrayOf(
                    httpException500,
                    NetworkResult.Error(
                        type = ErrorType.UNKNOWN,
                        message = NetworkErrorMessages.format(
                            httpException500,
                            NetworkErrorMessages.httpError(500, httpException500.message())
                        ),
                        exception = httpException500,
                        httpStatusCode = 500,
                        rawErrorBody = null
                    )
                ),
                arrayOf(
                    RuntimeException("unexpected"),
                    NetworkResult.Error(
                        type = ErrorType.UNKNOWN,
                        message = NetworkErrorMessages.format(
                            RuntimeException("unexpected"),
                            NetworkErrorMessages.unknownError("unexpected")
                        ),
                        exception = RuntimeException("unexpected")
                    )
                )
            )
        }
    }

    private val testDispatcher = StandardTestDispatcher()

    @Test
    fun `safeApiCall returns Error from handleHttpError when api failed with HTTP error code`() =
        runTest(testDispatcher) {
            val result = safeApiCall<SafeApiCallWithSuccessStatusTest.DummyData>(dispatcher = testDispatcher) {
                throw exception
            }

            assertTrue(result is NetworkResult.Error)
            result as NetworkResult.Error

            assertEquals(expectedError.type, result.type)
            assertEquals(expectedError.message, result.message)
            assertEquals(expectedError.httpStatusCode, result.httpStatusCode)
        }
}