package com.app.recipehub.presentation.mapper

import androidx.annotation.StringRes
import com.app.domain.DomainError
import com.app.recipehub.presentation.R

/**
 * Maps a [DomainError] to a corresponding [StringRes] display message ID,
 * using strings specific to the RecipeHub feature.
 *
 * @return The [StringRes] Int ID for the display message.
 */
@StringRes
fun DomainError.toDisplayMessageResourceId(): Int { // Renamed to be more specific
    return when (this) {
        is DomainError.NetworkUnavailable -> R.string.recipehub_error_message_network_not_available
        is DomainError.AuthenticationFailed -> R.string.recipehub_error_message_authentication_failed
        is DomainError.AccessDenied -> R.string.recipehub_error_message_access_denied
        is DomainError.ResourceNotFound -> R.string.recipehub_error_message_resource_not_found
        is DomainError.ServerError -> R.string.recipehub_error_message_server_error
        is DomainError.InvalidUserInput -> R.string.recipehub_error_message_invalid_user_input
        is DomainError.DataProcessingError -> R.string.recipehub_error_message_data_processing
        is DomainError.RequestConflict -> R.string.recipehub_error_message_request_conflict
        else ->  R.string.recipehub_error_message_unknown
    }
}
