package com.silverbullet.samtasks.ui.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.silverbullet.samtasks.databinding.LoginFragmentBinding
import com.silverbullet.samtasks.utils.errorMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding
    private lateinit var navController: NavController
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        initClickListeners()
        binding.apply {
            lifecycleOwner = this@LoginFragment
            loginViewModel = this@LoginFragment.loginViewModel
        }
        observe()
    }

    private fun initClickListeners() {
        binding.signUpTextButton.setOnClickListener {
            navController.navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
        }
    }

    /**
     * initializes observers for livedata in viewModel
     */
    private fun observe() {
        loginViewModel.emailError.observe(
            viewLifecycleOwner,
            Observer { binding.emailEdit.errorMessage(it) }
        )
        loginViewModel.passwordError.observe(
            viewLifecycleOwner,
            Observer { binding.passwordEdit.errorMessage(it) }
        )
    }
}