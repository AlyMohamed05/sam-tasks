package com.silverbullet.samtasks.utils

import com.google.firebase.auth.FirebaseUser
import com.silverbullet.samtasks.data.models.User

fun FirebaseUser.toUser(): User {
    return User(
     name = displayName,
     email = email
    )
}