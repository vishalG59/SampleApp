package com.app.network.resource

/**
 * [NetworkResult] represents the result of a network operation.
 * It has two possible states [Success] and [Error].
 */
sealed class NetworkResult<out T> {
    /**
     * [Success] Represents a successful network operation.
     *
     * @param data The successful data result.
     */
    data class Success<out T>(val data: T) : NetworkResult<T>()
    /**
     * [Error] Represents a failure/error state.
     *
     * @param type The general category of the error.
     * @param message A descriptive message about the error. Can be used for logging
     * @param httpStatusCode The HTTP status code if the error is from an HTTP request.
     * @param exception The actual Throwable that was caught, for detailed debugging.
     * @param rawErrorBody The raw error body content as a string, if available and parseable.
     */
    data class Error(
        val type: ErrorType,
        val message: String, // Consolidated message field
        val httpStatusCode: Int? = null,
        val exception: Throwable? = null,
        val rawErrorBody: String? = null
    ) : NetworkResult<Nothing>()
}

/**
 * [ErrorType] represents the general category of an network error.
 */
enum class ErrorType {
    NO_CONNECTION, TIMEOUT, NETWORK_UNAVAILABLE,
    BAD_REQUEST, UNAUTHORIZED, FORBIDDEN, NOT_FOUND, METHOD_NOT_ALLOWED, CONFLICT, VALIDATION_ERROR,
    INTERNAL_SERVER_ERROR, SERVICE_UNAVAILABLE, GATEWAY_TIMEOUT,
    API_CONTRACT_ERROR, EMPTY_RESPONSE, SERIALIZATION,
    UNKNOWN
}
