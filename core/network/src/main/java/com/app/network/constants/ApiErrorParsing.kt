package com.app.network.constants

/**
 *  [ApiErrorParsing] use to provide relevant error messages format.
 */
object ApiErrorParsing {

    const val FIELD_MESSAGE = "message"
    const val FIELD_ERROR = "error"
    const val FIELD_DETAIL = "detail"

    const val SERVER_MESSAGE_TEMPLATE = "Server Message (HTTP %d): %s"
    const val PARSE_FAILURE_SUFFIX = " (Could not parse error body as JSON)"
}