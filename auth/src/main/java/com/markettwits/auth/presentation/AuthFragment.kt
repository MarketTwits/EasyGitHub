package com.markettwits.auth.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.markettwits.auth.R
import com.markettwits.auth.databinding.FragmentAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class AuthFragment : Fragment(R.layout.fragment_auth), AuthUiStateHandle.Response {
    private lateinit var binding: FragmentAuthBinding
    private val viewModel by viewModels<AuthViewModel.Base>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeAuth(viewLifecycleOwner) {
            it.handle(this)
        }
        binding.submitButtonLayout.setButtonClickListener {
            viewModel.trySignIn(binding.inputEditText.text.toString())
        }
    }

    override fun success() {
        viewModel.navigateTo()
    }

    override fun error(message: Int) {
        ErrorDialogFragment(message, viewModel).show(childFragmentManager, ErrorDialogFragment.TAG)
    }

}