package com.app.network.constants


/**
 * [LogMessages] use to provide relevant log messages.
 */
object LogMessages {
    const val SAFE_API_CALL_EXCEPTION = "SafeApiCall caught an exception for type %s"
    const val NULL_RESPONSE_BODY_WARNING = "API returned successful status code %d but response body was null for type %s."
    const val READ_ERROR_BODY = "Read error body: %s"
    const val ERROR_READING_RESPONSE_BODY = "Error reading ResponseBody to string"
    const val LOG_NO_STANDARD_FIELD = "HTTP %d. Error body did not contain a standard message field: %s"
    const val LOG_JSON_PARSE_ERROR = "Could not parse error body as JSON for detailed message. Body: %s"
}