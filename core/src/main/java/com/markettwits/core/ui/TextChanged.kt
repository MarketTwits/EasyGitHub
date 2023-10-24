package com.markettwits.core.ui

import android.text.Editable
import android.text.TextWatcher

interface TextChanged : TextWatcher{
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)
    override fun afterTextChanged(p0: Editable?) = Unit
}