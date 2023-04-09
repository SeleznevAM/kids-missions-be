package ru.syrupmg.modules.auth

import ru.syrupmg.models.req.ConfirmRequest
import ru.syrupmg.models.req.RequestCodeModel
import ru.syrupmg.models.res.AuthSessionModel
import ru.syrupmg.modules.auth.locations.Auth
import ru.syrupmg.modules.auth.locations.ConfirmCode
import ru.syrupmg.modules.auth.locations.Login
import ru.syrupmg.modules.auth.locations.RequestCode
import ru.syrupmg.modules.auth.sessions.AuthSessionController
import ru.syrupmg.repos.UserRepository
import ru.syrupmg.services.ApiServer
import ru.syrupmg.services.JwtGenerator
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.locations.post
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.joda.time.DateTime
import org.joda.time.Seconds

fun ApiServer.auth() = routing {
    val controller = AuthSessionController()
    val jwtGenerator = JwtGenerator()
    val userRepository = UserRepository()

    location<Auth> {
        location<Login> {
            post<RequestCode> {
                val phone = call.receive<RequestCodeModel>()
                val session = controller.createSession(phone.phone_number)
                call.respond(AuthSessionModel(Seconds.secondsBetween(DateTime.now(), session.expireAt).seconds))
            }

            post<ConfirmCode> {
                val req = call.receive<ConfirmRequest>()

                val session = controller.getSessionByPhoneNumber(req.phone_number)

                if(session?.code == req.code) {
                    val user = userRepository.getUserByPhoneNumber(req.phone_number) ?: userRepository.createUser(req.phone_number)
                    val tokens = jwtGenerator.authInfo(user.id)
                    controller.deleteSession(session)
                    call.respond(tokens)
                } else {
                    call.respond(HttpStatusCode.BadRequest)
                }

            }
        }
    }
}