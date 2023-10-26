package com.markettwits.auth.presentation.input_edit_text

import android.content.Context
import android.util.AttributeSet
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputEditText
import com.markettwits.auth.presentation.AuthFragment
import com.markettwits.auth.presentation.AuthViewModel
import com.markettwits.core.ui.TextChanged

internal class TokenEditTextLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {
    private val root by lazy { findFragment<AuthFragment>() }
    private lateinit var viewModel: AuthViewModel
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        this.addTextChangedListener(object : TextChanged {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.validate(p0.toString())
            }
        })
        viewModel = root.viewModels<AuthViewModel.Base>().value
    }
}