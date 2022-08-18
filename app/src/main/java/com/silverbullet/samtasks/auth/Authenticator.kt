package com.silverbullet.samtasks.auth

import androidx.lifecycle.LiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.silverbullet.samtasks.data.models.User

interface Authenticator {

    val user: User?
    val isSignedIn: LiveData<Boolean>

    suspend fun createNewUser(
        name: String,
        email: String,
        password: String
    ): SignupResult

    suspend fun login(
        email: String,
        password: String
    ): LoginResult

    suspend fun loginWithCredentials(account: GoogleSignInAccount): LoginResult
}