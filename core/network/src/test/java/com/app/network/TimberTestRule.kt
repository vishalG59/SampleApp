package com.app.network

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import timber.log.Timber

/**
 * JUnit TestRule that plants a Timber debug tree before tests and unplants it after.
 */
class TimberTestRule : TestRule {
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                Timber.plant(object : Timber.DebugTree() {
                    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                        // You can customize logging output for tests here if needed
                        println("TimberTest: [$tag] $message ${t?.localizedMessage ?: ""}")
                    }
                })
                try {
                    base.evaluate()
                } finally {
                    Timber.uprootAll()
                }
            }
        }
    }
}