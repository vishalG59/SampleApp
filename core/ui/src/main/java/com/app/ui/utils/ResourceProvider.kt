package com.app.ui.utils


import androidx.annotation.StringRes

/**
 * Interface for providing access to app resources.
 * This allows for easier testing by mocking resource access.
 */
interface ResourceProvider {
    fun getString(@StringRes stringResId: Int): String
    fun getString(@StringRes stringResId: Int, vararg formatArgs: Any): String
}
