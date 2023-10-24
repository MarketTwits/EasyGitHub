package com.markettwits.auth.presentation.submit

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.markettwits.core.R
import com.markettwits.auth.databinding.SubmitButtonLayoutBinding
import com.markettwits.auth.presentation.AuthFragment
import com.markettwits.auth.presentation.AuthUiState
import com.markettwits.auth.presentation.AuthUiStateHandle
import com.markettwits.auth.presentation.AuthViewModel
import com.markettwits.auth.presentation.validation.TokenValidationState
import com.markettwits.auth.presentation.validation.TokenValidationStateHandle

abstract class SubmitAuthButtonViewBase @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs), TokenValidationStateHandle {
    protected val binding =
        SubmitButtonLayoutBinding.inflate(LayoutInflater.from(context), this, false)
    protected val root by lazy { findFragment<AuthFragment>() }
    protected val viewModel by lazy { root.viewModels<AuthViewModel.Base>() }
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewModel.value.observeAuth(findViewTreeLifecycleOwner()!!) {
            viewModel.value.provide(it)
        }
        viewModel.value.observeValidation(findViewTreeLifecycleOwner()!!) {
            it.handle(this)
        }
        addView(binding.root)
    }

    override fun handle(enabled: Boolean, message: Int, buttonTitle: Int, progress: Int) {
        binding.submitButton.isEnabled = enabled
        binding.submitButton.setText(buttonTitle)
        binding.progressCircular.visibility = progress
    }

    abstract fun setButtonClickListener(listener: OnClickListener?)
}