package com.app.network.resource

import com.app.network.constants.NetworkErrorMessages
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

@RunWith(Parameterized::class)
class SafeApiCallWithNetworkExceptionTest   (
    private val exception: Throwable,
    private val expectedError: NetworkResult.Error
) {

    companion object {

        @JvmStatic
        @Parameterized.Parameters(name = "{index}: Exception = {0}")
        fun data(): Collection<Array<Any>> {
            val socketTimeoutException = SocketTimeoutException("timeout")
            val unknownHostException = UnknownHostException("unknown host")
            val connectException = ConnectException("connection refused")
            val sslHandshakeException = SSLHandshakeException("SSL handshake failed")
            val ioException = IOException("I/O error occurred")
            val jsonSyntaxException = JsonSyntaxException("syntax error")
            val jsonIOException = JsonIOException("IO error")
            val jsonParseException = JsonParseException("parse error")
            val illegalStateException = IllegalStateException("illegal state")

            return listOf(
                arrayOf(
                    socketTimeoutException,
                    NetworkResult.Error(
                        type = ErrorType.TIMEOUT,
                        message = NetworkErrorMessages.format(socketTimeoutException, NetworkErrorMessages.TIMEOUT),
                        exception = socketTimeoutException
                    )
                ),
                arrayOf(
                    unknownHostException,
                    NetworkResult.Error(
                        type = ErrorType.NO_CONNECTION,
                        message = NetworkErrorMessages.format(unknownHostException, NetworkErrorMessages.NO_CONNECTION_UNKNOWN_HOST),
                        exception = unknownHostException
                    )
                ),
                arrayOf(
                    connectException,
                    NetworkResult.Error(
                        type = ErrorType.NO_CONNECTION,
                        message = NetworkErrorMessages.format(connectException, NetworkErrorMessages.NO_CONNECTION_REFUSED),
                        exception = connectException
                    )
                ),
                arrayOf(
                    sslHandshakeException,
                    NetworkResult.Error(
                        type = ErrorType.NETWORK_UNAVAILABLE,
                        message = NetworkErrorMessages.format(sslHandshakeException, NetworkErrorMessages.SSL_ERROR),
                        exception = sslHandshakeException
                    )
                ),
                arrayOf(
                    ioException,
                    NetworkResult.Error(
                        type = ErrorType.NETWORK_UNAVAILABLE,
                        message = NetworkErrorMessages.format(ioException, NetworkErrorMessages.NETWORK_ERROR),
                        exception = ioException
                    )
                ),
                arrayOf(
                    jsonSyntaxException,
                    NetworkResult.Error(
                        type = ErrorType.SERIALIZATION,
                        message = NetworkErrorMessages.format(jsonSyntaxException, NetworkErrorMessages.SERIALIZATION_ERROR),
                        exception = jsonSyntaxException
                    )
                ),
                arrayOf(
                    jsonIOException,
                    NetworkResult.Error(
                        type = ErrorType.SERIALIZATION,
                        message = NetworkErrorMessages.format(jsonIOException, NetworkErrorMessages.SERIALIZATION_ERROR),
                        exception = jsonIOException
                    )
                ),
                arrayOf(
                    jsonParseException,
                    NetworkResult.Error(
                        type = ErrorType.SERIALIZATION,
                        message = NetworkErrorMessages.format(jsonParseException, NetworkErrorMessages.SERIALIZATION_ERROR),
                        exception = jsonParseException
                    )
                ),
                arrayOf(
                    illegalStateException,
                    NetworkResult.Error(
                        type = ErrorType.SERIALIZATION,
                        message = NetworkErrorMessages.format(illegalStateException, NetworkErrorMessages.SERIALIZATION_ERROR),
                        exception = illegalStateException
                    )
                )
            )
        }
    }

    private val testDispatcher = StandardTestDispatcher()

    @Test
    fun `safeApiCall returns Error from mapThrowableToErrorResult when exception is thrown`() = runTest(testDispatcher) {
        val result = safeApiCall<SafeApiCallWithSuccessStatusTest.DummyData>(dispatcher = testDispatcher) {
            throw exception
        }

        assertTrue(result is NetworkResult.Error)
        result as NetworkResult.Error

        assertEquals(expectedError.type, result.type)
        assertEquals(expectedError.message, result.message)
        assertEquals(expectedError.exception!!::class, result.exception!!::class)

        // Check httpStatusCode if available
        if (expectedError.httpStatusCode != null) {
            assertEquals(expectedError.httpStatusCode, result.httpStatusCode)
        } else {
            assertNull(result.httpStatusCode)
        }
    }
}
