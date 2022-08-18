package com.silverbullet.samtasks.auth

enum class LoginResult{
    SUCCESS,
    WRONG_CREDENTIALS,
    NETWORK_ERROR,
    UNKNOWN_ERROR
}

enum class SignupResult{
    SUCCESS,
    ACCOUNT_ALREADY_EXISTS,
    NETWORK_ERROR,
    UNKNOWN_ERROR
}
