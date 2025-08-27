package com.app.network.constants

/**
 * [HttpErrorMessages] use to provide relevant error messages for httpsError codes(ex 4xx,5xx).
 */
object HttpErrorMessages {
        const val BAD_REQUEST = "Bad Request: The server could not understand the request due to invalid syntax."
        const val UNAUTHORIZED = "Unauthorized: Authentication is required and has failed or has not yet been provided."
        const val FORBIDDEN = "Forbidden: You do not have permission to access this resource."
        const val NOT_FOUND = "Not Found: The requested resource could not be found."
        const val METHOD_NOT_ALLOWED = "Method Not Allowed: The request method is not supported for the requested resource."
        const val TIMEOUT = "Request Timeout: The server timed out waiting for the request."
        const val CONFLICT = "Conflict: The request could not be completed due to a conflict with the current state of the resource."
        const val VALIDATION_ERROR = "Unprocessable Entity: The request was well-formed but was unable to be followed due to semantic errors."
        const val CLIENT_ERROR = "Client Error: An error occurred on the client side (code %d)."
        const val INTERNAL_SERVER_ERROR = "Internal Server Error: The server encountered an unexpected condition."
        const val BAD_GATEWAY = "Bad Gateway: The server received an invalid response from an upstream server."
        const val SERVICE_UNAVAILABLE = "Service Unavailable: The server is currently unable to handle the request."
        const val GATEWAY_TIMEOUT = "Gateway Timeout: The server did not receive a timely response from an upstream server."
        const val SERVER_ERROR = "Server Error: An error occurred on the server side (code %d)."
        const val UNKNOWN = "An unknown HTTP error occurred (code %d)."
}