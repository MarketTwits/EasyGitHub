package com.markettwits.auth.presentation.input_edit_text

import android.content.Context
import android.util.AttributeSet
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.google.android.material.textfield.TextInputLayout
import com.markettwits.auth.presentation.AuthFragment
import com.markettwits.auth.presentation.AuthViewModel
import com.markettwits.auth.presentation.validation.TokenValidationStateHandle

internal class TokenInputLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextInputLayout(context, attrs), TokenValidationStateHandle {

    private val root by lazy { findFragment<AuthFragment>() }
    private val viewModel by lazy { root.viewModels<AuthViewModel.Base>() }
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewModel.value.observeValidation(findViewTreeLifecycleOwner()!!) {
            it.handle(this)
        }
    }
    override fun handle(enabled: Boolean, message: Int, buttonTitle: Int, progress: Int) {
        if (!enabled) this.error = context.getString(message) else this.error = null
    }
}