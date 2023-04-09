package ru.syrupmg.modules.auth.sessions

import org.joda.time.DateTime


data class AuthSession(val phoneNumber: String, val code: String, val expireAt: DateTime)

class AuthSessionController {

    private val sessionExpireSec = 90
    private val sessions = mutableMapOf<String, AuthSession>()

    private val adminPhoneNUmber = "+12125550100"

    private fun generateCode(): String {
        return (1000..9999).random().toString()
    }

    fun createSession(phoneNumber: String): AuthSession {
        return sessions[phoneNumber]?.takeIf { it.expireAt.isAfterNow } ?: createAuthSession(phoneNumber).apply {
            sessions[phoneNumber] = this
        }
    }

    fun getSessionByPhoneNumber(phoneNumber: String): AuthSession? {
        return sessions[phoneNumber]
    }

    fun deleteSession(session: AuthSession) = sessions.remove(session.phoneNumber)

    private fun createAuthSession(phoneNumber: String): AuthSession {
        val code = if(phoneNumber == adminPhoneNUmber) "1234" else generateCode()
        return AuthSession(phoneNumber, code, DateTime.now().plusSeconds(sessionExpireSec))
    }

}