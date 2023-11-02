package com.markettwits.core.ui.save_and_restore

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

fun <T>  mapToSaveState(list : List<T>) : ArrayList<T >{
    val new = arrayListOf<T>()
    new.addAll(list)
    return new
}
inline fun <reified T : Parcelable> Bundle.parcelableArrayList(key: String): ArrayList<T>? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelableArrayList(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayList(key)
}
inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}