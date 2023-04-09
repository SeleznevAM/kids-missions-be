package ru.syrupmg

import ru.syrupmg.modules.auth.auth
import ru.syrupmg.services.ApiServer

fun main() {
    Application.run()
}


object Application {
    private val server = ApiServer(8080)

    fun run() {
        server.auth()
        server.start()
    }
}