package com.devm7mdibrahim.domain.entities

data class AuthData(
    val token: String,
    val user: UserData
)

data class UserData(
    val name: String,
    val phone: String,
    val email: String
)
