package com.markettwits.core.wrappers

import android.content.Context
import android.os.Build

interface ManageResource {
    fun string(id : Int) : String
    fun color(id : Int) : Int
    class Base(private val context: Context) : ManageResource {
        override fun string(id: Int) = context.getString(id)
        override fun color(id: Int): Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getColor(id)
        } else {
            context.resources.getColor(id)
        }
    }
}