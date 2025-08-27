package com.app.network.dispatcher

/**
 * [AppDispatchers] define abstracts for providing coroutines dispatchers.
 */
interface AppDispatchers {
    fun io(): kotlinx.coroutines.CoroutineDispatcher
    fun main(): kotlinx.coroutines.CoroutineDispatcher
    fun default(): kotlinx.coroutines.CoroutineDispatcher
}
