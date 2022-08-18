package com.silverbullet.samtasks.ui.activities.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.silverbullet.samtasks.R
import com.silverbullet.samtasks.ui.activities.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                shouldShowSplashScreen()
            }
            setOnExitAnimationListener {
                Intent(this@HomeActivity, AuthActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }
        }
        setContentView(R.layout.activity_home)
    }

    /**
     * returns the condition to decide whether to show splash screen or not.
     * if user should authenticate it will navigate to Auth Activity.
     */
    private fun shouldShowSplashScreen(): Boolean {
        return when (homeViewModel.accountState.value!!) {
            AccountState.Loading -> true
            AccountState.LoggedIn -> false
            AccountState.Anonymous -> false
            AccountState.Authenticate -> false

        }
    }
}