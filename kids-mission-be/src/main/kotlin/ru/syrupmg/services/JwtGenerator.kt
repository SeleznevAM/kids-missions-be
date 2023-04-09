package ru.syrupmg.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import ru.syrupmg.models.res.Tokens
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Hours
import java.util.*

class JwtGenerator {
    private val realm = "kidsMissions"
    private val audience = "api"
    private val issuer = "https://kids.missions"
    private val secret = "AmEWWwUeawqSDQf8lY3JdQqIp1zxcgnfUFfU6GVN"

    private val accessPeriod = Hours.hours(10).toPeriod()
    private val refreshPeriod = Days.days(60).toPeriod()

    enum class Subject {
        Access, Refresh;
    }

    private val algorithm = Algorithm.HMAC512(secret)

    fun authInfo(userId: String) = Tokens(
        userToken(userId, accessExpiration(), Subject.Access),
        userToken(userId, refreshExpiration(), Subject.Refresh)
    )

    private fun userToken(userId: String, expire: Date, subject: Subject): String = JWT
        .create()
        .withSubject(subject.name)
        .withAudience(audience)
        .withIssuer(issuer)
        .withClaim("user", userId)
        .withExpiresAt(expire)
        .sign(algorithm)

    private fun accessExpiration() = (DateTime.now() + accessPeriod).toDate()
    private fun refreshExpiration() = (DateTime.now() + refreshPeriod).toDate()
}