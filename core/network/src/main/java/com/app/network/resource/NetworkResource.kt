package com.app.network.resource

import com.app.network.constants.ApiErrorParsing
import com.app.network.constants.HttpErrorMessages
import com.app.network.constants.LogMessages
import com.app.network.constants.NetworkErrorMessages
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException


/**
 * A generic suspend function to safely execute API calls and return a [NetworkResult].
 * This function is intended for use in a RemoteSource or similar data layer component
 * where a single API call result is needed.
 *
 * @param ResultType The type of the successful response body.
 * @param dispatcher The CoroutineDispatcher on which the API call will be executed. Defaults to Dispatchers.IO.
 * @param execute A suspend lambda function that performs the Retrofit API call and returns a Response.
 * @return A [NetworkResult] which is either [NetworkResult.Success] or [NetworkResult.Error].
 */
suspend inline fun <reified ResultType> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    crossinline execute: suspend () -> Response<ResultType>
): NetworkResult<ResultType> {
    return withContext(dispatcher) {
        try {
            val response: Response<ResultType> = execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    NetworkResult.Success(body)
                } else {
                    val errorMessage = LogMessages.NULL_RESPONSE_BODY_WARNING.format(
                        response.code(),
                        ResultType::class.java.simpleName
                    )
                    Timber.w(errorMessage)
                    NetworkResult.Error(
                        type = ErrorType.EMPTY_RESPONSE,
                        message = errorMessage,
                        httpStatusCode = response.code()
                    )
                }
            } else {
                handleHttpError(response)
            }
        } catch (throwable: Throwable) {
            Timber.e(
                throwable,
                LogMessages.SAFE_API_CALL_EXCEPTION.format(ResultType::class.java.simpleName)
            )
            mapThrowableToErrorResult(throwable)
        }
    }
}

/**
 * Handles HTTP error responses (4xx, 5xx) from Retrofit.
 */
fun <ResultType> handleHttpError(response: Response<ResultType>): NetworkResult.Error {
    val httpStatusCode = response.code()
    val errorBodyString = response.errorBody()?.safeReadToString()
    val errorType: ErrorType
    var standardMessage: String
    when (httpStatusCode) {
        400 -> {
            errorType = ErrorType.BAD_REQUEST
            standardMessage = HttpErrorMessages.BAD_REQUEST
        }
        401 -> {
            errorType = ErrorType.UNAUTHORIZED
            standardMessage = HttpErrorMessages.UNAUTHORIZED
        }
        403 -> {
            errorType = ErrorType.FORBIDDEN
            standardMessage = HttpErrorMessages.FORBIDDEN
        }
        404 -> {
            errorType = ErrorType.NOT_FOUND
            standardMessage = HttpErrorMessages.NOT_FOUND
        }
        405 -> {
            errorType = ErrorType.METHOD_NOT_ALLOWED
            standardMessage = HttpErrorMessages.METHOD_NOT_ALLOWED
        }
        408 -> {
            errorType = ErrorType.TIMEOUT
            standardMessage = HttpErrorMessages.TIMEOUT
        }
        409 -> {
            errorType = ErrorType.CONFLICT
            standardMessage = HttpErrorMessages.CONFLICT
        }
        422 -> {
            errorType = ErrorType.VALIDATION_ERROR
            standardMessage = HttpErrorMessages.VALIDATION_ERROR
        }
        in 400..499 -> {
            errorType = ErrorType.BAD_REQUEST
            standardMessage = HttpErrorMessages.CLIENT_ERROR.format(httpStatusCode)
        }
        500 -> {
            errorType = ErrorType.INTERNAL_SERVER_ERROR
            standardMessage = HttpErrorMessages.INTERNAL_SERVER_ERROR
        }
        502 -> {
            errorType = ErrorType.NETWORK_UNAVAILABLE
            standardMessage = HttpErrorMessages.BAD_GATEWAY
        }
        503 -> {
            errorType = ErrorType.SERVICE_UNAVAILABLE
            standardMessage = HttpErrorMessages.SERVICE_UNAVAILABLE
        }
        504 -> {
            errorType = ErrorType.GATEWAY_TIMEOUT
            standardMessage = HttpErrorMessages.GATEWAY_TIMEOUT
        }
        in 500..599 -> {
            errorType = ErrorType.INTERNAL_SERVER_ERROR
            standardMessage = HttpErrorMessages.SERVER_ERROR.format(httpStatusCode)
        }
        else -> {
            errorType = ErrorType.UNKNOWN
            standardMessage = HttpErrorMessages.UNKNOWN.format(httpStatusCode)
        }
    }

    if (!errorBodyString.isNullOrBlank()) {
        try {
            val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
            val apiErrorMessage = jsonObject[ApiErrorParsing.FIELD_MESSAGE]?.asString
                ?: jsonObject[ApiErrorParsing.FIELD_ERROR]?.asString
                ?: jsonObject[ApiErrorParsing.FIELD_DETAIL]?.asString

            if (!apiErrorMessage.isNullOrBlank()) {
                standardMessage = ApiErrorParsing.SERVER_MESSAGE_TEMPLATE.format(httpStatusCode, apiErrorMessage)
            } else {
                Timber.d(LogMessages.LOG_NO_STANDARD_FIELD.format(httpStatusCode, errorBodyString))
            }
        } catch (e: Exception) {
            Timber.w(e, LogMessages.LOG_JSON_PARSE_ERROR.format(errorBodyString))
            standardMessage += ApiErrorParsing.PARSE_FAILURE_SUFFIX
        }
    }

    return NetworkResult.Error(
        type = errorType,
        message = standardMessage,
        httpStatusCode = httpStatusCode,
        rawErrorBody = errorBodyString
    )
}

/**
 * Maps non-HTTP Throwables (IOExceptions, etc.) to a [NetworkResult.Error].
 */
fun mapThrowableToErrorResult(throwable: Throwable): NetworkResult.Error {
    val errorType: ErrorType
    var standardMessage: String

    when (throwable) {
        is SocketTimeoutException -> {
            errorType = ErrorType.TIMEOUT
            standardMessage = NetworkErrorMessages.TIMEOUT
        }

        is UnknownHostException -> {
            errorType = ErrorType.NO_CONNECTION
            standardMessage = NetworkErrorMessages.NO_CONNECTION_UNKNOWN_HOST
        }

        is ConnectException -> {
            errorType = ErrorType.NO_CONNECTION
            standardMessage = NetworkErrorMessages.NO_CONNECTION_REFUSED
        }

        is SSLHandshakeException -> {
            errorType = ErrorType.NETWORK_UNAVAILABLE
            standardMessage = NetworkErrorMessages.SSL_ERROR
        }

        is IOException -> {
            errorType = ErrorType.NETWORK_UNAVAILABLE
            standardMessage = NetworkErrorMessages.NETWORK_ERROR
        }

        is JsonSyntaxException, is JsonIOException, is JsonParseException, is IllegalStateException -> {
            errorType = ErrorType.SERIALIZATION
            standardMessage = NetworkErrorMessages.SERIALIZATION_ERROR
        }

        is HttpException -> {
            val httpCode = throwable.code()
            errorType = when (httpCode) {
                401 -> ErrorType.UNAUTHORIZED
                403 -> ErrorType.FORBIDDEN
                else -> ErrorType.UNKNOWN
            }
            standardMessage = NetworkErrorMessages.httpError(httpCode, throwable.message())
        }

        else -> {
            errorType = ErrorType.UNKNOWN
            standardMessage = NetworkErrorMessages.unknownError(throwable.localizedMessage)
        }
    }

    return NetworkResult.Error(
        type = errorType,
        message = NetworkErrorMessages.format(throwable, standardMessage),
        exception = throwable,
        httpStatusCode = (throwable as? HttpException)?.code(),
        rawErrorBody = (throwable as? HttpException)?.response()?.errorBody()?.safeReadToString()
    )
}

/**
 * Safely reads the ResponseBody to a String and closes it.
 */
fun ResponseBody.safeReadToString(): String? {
    return try {
        this.string().also {
            Timber.v(LogMessages.READ_ERROR_BODY.format(it))
        }
    } catch (e: Exception) {
        Timber.e(e, LogMessages.ERROR_READING_RESPONSE_BODY)
        null
    } finally {
        try {
            this.close()
        } catch (e: Exception) {
            // Ignore close exceptions
        }
    }
}
