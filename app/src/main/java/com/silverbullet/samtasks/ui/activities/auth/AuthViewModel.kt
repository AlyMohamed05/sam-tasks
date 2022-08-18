package com.silverbullet.samtasks.ui.activities.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.silverbullet.samtasks.auth.Authenticator
import com.silverbullet.samtasks.auth.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authenticator: Authenticator
) :ViewModel(){

    val isSignedIn: LiveData<Boolean>
        get() = authenticator.isSignedIn

    fun loginWithCredentials(account: GoogleSignInAccount){
        viewModelScope.launch {
            val result = authenticator.loginWithCredentials(account)
            if(result == LoginResult.SUCCESS){
                Timber.d("Request succeeded !")
            }else{
                Timber.d("Request failed")
            }
        }
    }
}