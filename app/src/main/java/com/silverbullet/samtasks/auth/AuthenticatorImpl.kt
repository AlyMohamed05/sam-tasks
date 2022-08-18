package com.silverbullet.samtasks.auth

import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.silverbullet.samtasks.data.models.User
import com.silverbullet.samtasks.utils.toUser
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.lang.Exception

@Suppress("unused")
class AuthenticatorImpl : Authenticator {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private var firebaseUser: FirebaseUser? = firebaseAuth.currentUser

    override val user: User?
        get() = firebaseUser?.toUser()

    override val isSignedIn: Boolean
        get() = user != null

    override suspend fun createNewUser(
        name: String,
        email: String,
        password: String
    ): SignupResult {
        return try {
            val task = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            firebaseUser = task.user
            SignupResult.SUCCESS
        } catch (e: FirebaseException) {
            if (e is FirebaseAuthUserCollisionException) {
                SignupResult.ACCOUNT_ALREADY_EXISTS
            } else {
                Timber.d(e)
                SignupResult.UNKNOWN_ERROR
            }
        } catch (e: Exception) {
            Timber.d(e)
            SignupResult.UNKNOWN_ERROR
        }
    }

    override suspend fun login(email: String, password: String): LoginResult {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            firebaseUser = result.user
            LoginResult.SUCCESS
        } catch (e: FirebaseException) {
            if (e is FirebaseAuthInvalidUserException || e is FirebaseAuthInvalidCredentialsException) {
                LoginResult.WRONG_CREDENTIALS
            } else {
                LoginResult.UNKNOWN_ERROR
            }
        } catch (e: Exception) {
            Timber.d(e)
            LoginResult.UNKNOWN_ERROR
        }
    }
}