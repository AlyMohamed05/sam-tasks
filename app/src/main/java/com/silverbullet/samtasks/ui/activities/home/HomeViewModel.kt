package com.silverbullet.samtasks.ui.activities.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This viewModel is mainly used to decide if to stay on the home activity,
 * or app should navigate to the Auth Activity to login in user.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext context: Context
) : ViewModel() {

    private val _accountState= MutableLiveData(AccountState.Loading)
    val accountState: LiveData<AccountState>
        get() = _accountState

    init {
        fakeLoading()
    }

    private fun fakeLoading() {
        viewModelScope.launch {
            delay(1000L)
            _accountState.value = AccountState.Authenticate
        }
    }
}

/**
 * Loading : still Loading login status
 * Anonymous : User will continue without account
 * LoggedIn : Used is already logged in
 * Authenticate : app should navigate to login activity
 */
enum class AccountState{
    Loading,Anonymous,LoggedIn,Authenticate
}