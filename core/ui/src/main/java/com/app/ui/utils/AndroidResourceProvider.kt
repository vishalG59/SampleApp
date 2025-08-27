package com.app.ui.utils

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Android-specific implementation of [ResourceProvider].
 *
 * @param context is used to resolve resources.
 */
@Singleton
class AndroidResourceProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : ResourceProvider {

    override fun getString(@StringRes stringResId: Int): String {
        return context.getString(stringResId)
    }

    override fun getString(@StringRes stringResId: Int, vararg formatArgs: Any): String {
        return context.getString(stringResId, *formatArgs)
    }
}
