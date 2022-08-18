package com.silverbullet.samtasks.auth

import com.silverbullet.samtasks.data.models.User

interface Authenticator {

    val user: User?
    val isSignedIn: Boolean

    suspend fun createNewUser(
        name: String,
        email: String,
        password: String
    ): SignupResult

    suspend fun login(
        email: String,
        password: String
    ): LoginResult
}