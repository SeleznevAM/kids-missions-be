package ru.syrupmg.services

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.engine.*
import io.ktor.server.locations.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class ApiServer(port: Int) {
    private val engine: NettyApplicationEngine by lazy {
        embeddedServer(Netty, port = port) {
            install(ContentNegotiation) {
                json()
            }

            install(Locations)

            routing {
                modules.forEach {
                    it.invoke(this)
                }
            }

            routing {
                get("/") {
                    call.respond("HelloWorld!!")
                }
            }
        }

    }

    private val modules = arrayListOf<Routing.() -> Unit>()

    fun routing(configuration: Routing.() -> Unit) {
        modules.add(configuration)
    }

    fun start() = engine.start(true)
}