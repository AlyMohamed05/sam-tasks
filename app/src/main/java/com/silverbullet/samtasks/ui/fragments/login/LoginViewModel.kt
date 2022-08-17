package com.silverbullet.samtasks.ui.fragments.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext context: Context
) : ViewModel(){

    // LiveData that connects UI edit fields to ViewModel.
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
}