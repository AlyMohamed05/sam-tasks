package com.silverbullet.samtasks.ui.fragments.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.silverbullet.samtasks.databinding.SignupFragmentBinding
import com.silverbullet.samtasks.utils.errorMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment: Fragment() {

    private lateinit var binding : SignupFragmentBinding
    private lateinit var navController: NavController
    private val signupViewModel: SignupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SignupFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        initClickListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController  = findNavController()
        binding.apply {
            lifecycleOwner = this@SignupFragment
            signupViewModel = this@SignupFragment.signupViewModel
        }
        observe()
    }

    private fun initClickListeners(){
        binding.backTextButton.setOnClickListener{
            navController.navigateUp()
        }
    }

    private fun observe(){
        signupViewModel.nameError.observe(
            viewLifecycleOwner,
            Observer { binding.nameEdit.errorMessage(it) }
        )
        signupViewModel.emailError.observe(
            viewLifecycleOwner,
            Observer { binding.emailEdit.errorMessage(it) }
        )
        signupViewModel.passwordError.observe(
            viewLifecycleOwner,
            Observer { binding.passwordEdit.errorMessage(it) }
        )
    }
}