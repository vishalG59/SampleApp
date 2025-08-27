package com.app.network.constants

/**
 * [NetworkErrorMessages] use to provide relevant error messages format httpsError/exception/unknownError.
 *
 */
object NetworkErrorMessages {
    const val TIMEOUT = "Connection Timeout: The request timed out. Please check your internet connection."
    const val NO_CONNECTION_UNKNOWN_HOST = "No Connection: Cannot resolve host. Please check your internet connection."
    const val NO_CONNECTION_REFUSED = "Connection Refused: Failed to connect to the server. Please check your internet connection and server status."
    const val SSL_ERROR = "SSL Error: A problem occurred with the secure connection."
    const val NETWORK_ERROR = "Network Error: An I/O error occurred. Please check your internet connection."
    const val SERIALIZATION_ERROR = "Data Serialization Error: Failed to process data from the server. Response may be malformed."

    fun httpError(httpCode: Int, message: String?) =
        "HTTP Error (from Throwable): ${message ?: "Unknown"} (Code: $httpCode)"

    fun unknownError(msg: String?) =
        "An Unexpected Error Occurred: ${msg ?: "No specific error message."}"

    fun format(exception: Throwable, message: String): String {
        val exceptionName = exception.javaClass.simpleName
        return "Type: $exceptionName - $message"
    }
}
