package com.markettwits.auth.presentation.submit

import android.content.Context
import android.util.AttributeSet

internal class SubmitAuthButtonView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : SubmitAuthButtonViewBase(context, attrs) {

    override fun setButtonClickListener(listener: OnClickListener?) {
        binding.submitButton.setOnClickListener(listener)
    }
}