package com.app.domain

/**
 * [DomainError] is a sealed class that represents all possible errors that can occur in the domain layer.
 */
sealed class DomainError {
    object NetworkUnavailable : DomainError()
    object AuthenticationFailed : DomainError()
    object AccessDenied : DomainError()
    object NotFound : DomainError()
    object ServerError : DomainError()
    object InvalidInput : DomainError()
    object DataProcessingError : DomainError()
    object Conflict : DomainError()
    object ResourceNotFound : DomainError()
    object InvalidUserInput : DomainError()
    object RequestConflict : DomainError()
    data class Unknown(val message: String? = null, val cause: Throwable? = null) : DomainError()
}
