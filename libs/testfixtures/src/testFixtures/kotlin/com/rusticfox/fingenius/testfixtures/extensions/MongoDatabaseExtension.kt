package com.rusticfox.fingenius.testfixtures.extensions

import com.rusticfox.fingenius.testfixtures.utils.MongoDatabaseTestContainer
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

/**
 * A Database Extension that sets up and tears down a database before all tests in a class and after all tests
 */
class MongoDatabaseExtension : BeforeAllCallback, AfterAllCallback {
    private val database = MongoDatabaseTestContainer.init()

    override fun beforeAll(context: ExtensionContext) {
        runCatching { database.start() }
            .getOrElse {
                val testClassName = context.testClass.get().name
                println("$testClassName, failed with error $it")
            }
    }

    override fun afterAll(context: ExtensionContext) {
        if (database.isRunning) {
            database.stop()
        }
    }
}
