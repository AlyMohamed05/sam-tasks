package com.silverbullet.samtasks.utils.validator

import android.content.Context
import androidx.lifecycle.LiveData

interface FormValidator {

    val nameError: LiveData<String?>
    val emailError: LiveData<String?>
    val passwordError: LiveData<String?>

    /**
     * Initializes context obj to access string resources.
     * if ignoreField is true, field won't be validated.
     */
    fun initFormValidator(
        context: Context,
        ignoreName: Boolean = false,
        ignoreEmail: Boolean = false,
        ignorePassword: Boolean = false
    )

    /**
     * Runs local validations ( Doesn't need internet connection )
     */
    fun localValidate(
        name: String = "",
        email: String = "",
        password: String = ""
    ): Boolean

}