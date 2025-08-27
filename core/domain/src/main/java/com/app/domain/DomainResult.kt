package com.app.domain

/**
 * [DomainResult] is a sealed class that represents the result of a domain operation which will be
 * used by presentation layer.
 * It has three states which are [Success], [Failure] and [Loading] to consume by presentation layer.
 */
sealed class DomainResult<out D, out E : DomainError> {
    data class Success<out D>(val data: D) : DomainResult<D, Nothing>()
    data class Failure<out E : DomainError>(val error: E) : DomainResult<Nothing, E>()
    object Loading : DomainResult<Nothing, Nothing>()
}
