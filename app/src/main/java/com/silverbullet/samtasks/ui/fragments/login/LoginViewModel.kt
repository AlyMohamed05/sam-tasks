package com.silverbullet.samtasks.ui.fragments.login

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silverbullet.samtasks.auth.Authenticator
import com.silverbullet.samtasks.utils.validator.FormValidator
import com.silverbullet.samtasks.utils.validator.FormValidatorImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    app: Application,
    private val authenticator: Authenticator
) : ViewModel(),
    FormValidator by FormValidatorImpl() {

    @SuppressLint("StaticFieldLeak")
    private val context = app.applicationContext

    // LiveData that connects UI edit fields to ViewModel.
    val email = MutableLiveData("")
    val password = MutableLiveData("")


    init {
        initFormValidator(context, ignoreName = true)
    }

    fun login() {
        if (!localValidate(email = email.value!!, password = password.value!!)) {
            return
        }
        viewModelScope.launch {
            authenticator.login(email.value!!, password.value!!)
        }
    }

    fun continueWithGoogle() {
        // TODO : Implement this
    }

    fun continueWithoutLogin() {
        // TODO : Implement this
    }

}