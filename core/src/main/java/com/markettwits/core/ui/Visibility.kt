package com.markettwits.core.ui

import android.view.View

interface Visibility {

    fun apply(view: View)

    abstract class Abstract(private val visibility: Int) : Visibility {
        override fun apply(view: View) {
            view.visibility = visibility
        }
    }

    class Visible : Abstract(View.VISIBLE)
    class Gone : Abstract(View.GONE)
    class InVisible : Abstract(View.INVISIBLE)
}