package com.app.data

import com.app.domain.DomainError
import com.app.network.resource.ErrorType
import com.app.network.resource.NetworkResult
import timber.log.Timber

/**
 * Maps a Data Layer Error [NetworkResult.Error] to a Domain Layer Error [DomainError].
 */
fun NetworkResult.Error.toDomainError(): DomainError {
    Timber.e(
        this.exception,
        "DataLayer Error Details - Type: ${type}, HTTP: ${httpStatusCode}, Msg: ${message}, Body: ${rawErrorBody}"
    )

    return when (type) {
        ErrorType.NO_CONNECTION,
        ErrorType.NETWORK_UNAVAILABLE,
        ErrorType.TIMEOUT -> DomainError.NetworkUnavailable

        ErrorType.UNAUTHORIZED -> DomainError.AuthenticationFailed
        ErrorType.FORBIDDEN -> DomainError.AccessDenied
        ErrorType.NOT_FOUND -> DomainError.NotFound
        ErrorType.CONFLICT -> DomainError.Conflict

        ErrorType.BAD_REQUEST,
        ErrorType.VALIDATION_ERROR -> DomainError.InvalidInput

        ErrorType.INTERNAL_SERVER_ERROR,
        ErrorType.SERVICE_UNAVAILABLE,
        ErrorType.GATEWAY_TIMEOUT -> DomainError.ServerError

        ErrorType.SERIALIZATION,
        ErrorType.EMPTY_RESPONSE,
        ErrorType.API_CONTRACT_ERROR -> DomainError.DataProcessingError

        ErrorType.UNKNOWN -> DomainError.Unknown(
            message = "DataLayer: $message",
            cause = exception
        )

        else -> DomainError.Unknown(
            message = "DataLayer: $message",
            cause = exception
        )
    }
}

