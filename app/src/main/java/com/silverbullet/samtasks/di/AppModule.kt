package com.silverbullet.samtasks.di

import com.silverbullet.samtasks.auth.Authenticator
import com.silverbullet.samtasks.auth.AuthenticatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideAuthenticator(): Authenticator{
        return AuthenticatorImpl()
    }
}