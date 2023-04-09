package ru.syrupmg.models.res

@kotlinx.serialization.Serializable
data class Tokens(val accessToken: String, val refreshToken: String)