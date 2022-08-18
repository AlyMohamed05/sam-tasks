package com.silverbullet.samtasks.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
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

    private val _isSignedIn = MutableLiveData<Boolean>(user == null)
    override val isSignedIn: LiveData<Boolean>
        get() = _isSignedIn

    override suspend fun createNewUser(
        name: String,
        email: String,
        password: String
    ): SignupResult {
        return try {
            val task = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            firebaseUser = task.user
            _isSignedIn.value = true
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
            _isSignedIn.value = true
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

    override suspend fun loginWithCredentials(account: GoogleSignInAccount): LoginResult {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        return try {
            val result = firebaseAuth.signInWithCredential(credential).await()
            firebaseUser = result.user
            _isSignedIn.value = true
            LoginResult.SUCCESS
        } catch (e: Exception) {
            Timber.d(e)
            LoginResult.UNKNOWN_ERROR
        }
    }
}