package com.app.network.dispatcher

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * [AppDispatchersProvider] provides concreate implementation for coroutines dispatchers contract/abstraction.
 */
class AppDispatchersProvider @Inject constructor() : AppDispatchers {
    override fun io() = Dispatchers.IO
    override fun main() = Dispatchers.Main
    override fun default() = Dispatchers.Default
}
