package com.silverbullet.samtasks.utils

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorMessage")
fun TextInputLayout.errorMessage(error: String?) {
    if (error != null) {
        isErrorEnabled = true
        setError(error)
    } else {
        isErrorEnabled = false
        setError(null)
    }
}