package com.silverbullet.samtasks.utils.validator

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.silverbullet.samtasks.R
import java.lang.Exception

class FormValidatorImpl : FormValidator {

    /**
     * context must be initialized before using validator.
     * call initFormValidator() function to init context.
     */
    private lateinit var context: Context
    private var ignoreName = false
    private var ignoreEmail = false
    private var ignorePassword = false

    // Becomes true after initFormValidator() is called with need parameters.
    private var isInitialized = false

    private var _nameError: MutableLiveData<String?> = MutableLiveData(null)
    private var _emailError: MutableLiveData<String?> = MutableLiveData(null)
    private var _passwordError: MutableLiveData<String?> = MutableLiveData(null)

    override val nameError: LiveData<String?>
        get() = _nameError
    override val emailError: LiveData<String?>
        get() = _emailError
    override val passwordError: LiveData<String?>
        get() = _passwordError

    override fun initFormValidator(
        context: Context,
        ignoreName: Boolean,
        ignoreEmail: Boolean,
        ignorePassword: Boolean
    ) {
        this@FormValidatorImpl.context = context
        this@FormValidatorImpl.ignoreName = ignoreName
        this@FormValidatorImpl.ignoreEmail = ignoreEmail
        this@FormValidatorImpl.ignorePassword = ignorePassword
        isInitialized = true
    }

    /**
     * Returns true only if validation succeeded
     */
    override fun localValidate(
        name: String,
        email: String,
        password: String
    ): Boolean {
        ensureIsInitialized()
        var isValid = true
        if (!ignoreName) {
            val nameValidationResult = validateName(name)
            _nameError.value = nameValidationResult
            if (nameValidationResult != null) {
                isValid = false
            }
        }
        if (!ignoreEmail) {
            val emailValidationResult = validateEmailAddress(email)
            _emailError.value = emailValidationResult
            if (emailValidationResult != null) {
                isValid = false
            }
        }
        if (!ignorePassword) {
            val passwordValidationResult = validatePassword(password)
            _passwordError.value = passwordValidationResult
            if (passwordValidationResult != null) {
                isValid = false
            }
        }
        return isValid
    }

    /**
     * checks if initFormValidator() is called and if not it will throw exception
     */
    private fun ensureIsInitialized() {
        if (!isInitialized) {
            throw Exception("You need to call initFormValidator() first to init Validator")
        }
    }

    private fun validateName(name: String): String? {
        return if (name.isBlank()) {
            getErrorMessageOrNull(ValidationResult.EMPTY)
        } else {
            getErrorMessageOrNull(ValidationResult.VALID)
        }
    }

    /**
     * Returns error message if exists or null
     */
    private fun validateEmailAddress(email: String): String? {
        return if (email.isBlank()) {
            getErrorMessageOrNull(ValidationResult.EMPTY)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            getErrorMessageOrNull(ValidationResult.NOT_VALID_EMAIL)
        } else {
            getErrorMessageOrNull(ValidationResult.VALID)
        }
    }

    /**
     * Returns error message if exists or null
     */
    private fun validatePassword(password: String): String? {
        return if (password.isBlank()) {
            getErrorMessageOrNull(ValidationResult.EMPTY)
        } else if (password.length < 8) {
            getErrorMessageOrNull(ValidationResult.PASSWORD_TOO_SHORT)
        } else {
            getErrorMessageOrNull(ValidationResult.VALID)
        }
    }

    private fun getErrorMessageOrNull(result: ValidationResult): String? {
        return when (result) {
            ValidationResult.EMPTY -> context.getString(R.string.empty_field)
            ValidationResult.NOT_VALID_EMAIL -> context.getString(R.string.email_not_valid)
            ValidationResult.PASSWORD_TOO_SHORT -> context.getString(R.string.password_too_short)
            else -> null
        }
    }

}

enum class ValidationResult {
    EMPTY,
    NOT_VALID_EMAIL,

    PASSWORD_TOO_SHORT,

    VALID
}