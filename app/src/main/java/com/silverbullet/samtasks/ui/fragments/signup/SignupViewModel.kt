package com.silverbullet.samtasks.ui.fragments.signup

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
class SignupViewModel @Inject constructor(
    app: Application,
    private val authenticator: Authenticator
) : ViewModel(),
    FormValidator by FormValidatorImpl() {

    @SuppressLint("StaticFieldLeak")
    private val context = app.applicationContext

    // LiveData to manipulate input fields in UI
    val name = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")

    init {
        initFormValidator(context)
    }

    fun signup() {
        if (!localValidate(
                name = name.value!!,
                email = email.value!!,
                password = password.value!!
            )
        ) {
            return
        }
        viewModelScope.launch {
            authenticator.createNewUser(
                name.value!!,
                email.value!!,
                password.value!!
            )
        }
    }
}