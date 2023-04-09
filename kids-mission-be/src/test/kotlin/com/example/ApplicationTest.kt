package com.example

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.utils.EmptyContent.status
import io.ktor.server.testing.*
import kotlin.test.*
import io.ktor.http.*
import junit.framework.TestCase.assertEquals
import org.junit.Test
import ru.syrupmg.plugins.*
import ru.syrupmg.plugins.configureRouting

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }
}
