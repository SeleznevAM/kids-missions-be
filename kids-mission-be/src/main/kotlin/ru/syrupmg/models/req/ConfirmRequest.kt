package ru.syrupmg.models.req

@kotlinx.serialization.Serializable
data class ConfirmRequest(val phone_number: String, val code: String)
