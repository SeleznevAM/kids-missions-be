package ru.syrupmg.repos

import ru.syrupmg.models.User
import java.util.UUID

class UserRepository {
    private val users = arrayListOf<User>()

    fun createUser(phoneNumber: String): User {
        val user = User(UUID.randomUUID().toString(), phoneNumber)
        users.add(user)
        return user
    }


    fun getUserInfoById(id: String) = users.firstOrNull { it.id == id }

    fun getUserByPhoneNumber(phoneNumber: String) = users.firstOrNull { it.phone_number == phoneNumber }
}