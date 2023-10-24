package com.markettwits.auth.presentation.validation

import android.util.Log
import java.util.regex.Pattern

interface HandleValidationToken {
    fun handle(value : String) : TokenValidationState
    class Base : HandleValidationToken {
        override fun handle(value: String) : TokenValidationState {
            Log.d("mt05", "HandleValidationToken $value")
            val pattern = "^[a-zA-Z0-9_-]{0,45}$"
            return if (value.isEmpty()) TokenValidationState.Empty()
            else {
                val matcher = Pattern.compile(pattern).matcher(value)
                return if (matcher.matches()) TokenValidationState.Valid()
                else TokenValidationState.Invalid()
            }
        }
    }

}