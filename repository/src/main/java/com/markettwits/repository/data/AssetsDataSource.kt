package com.markettwits.repository.data

import android.content.Context
import java.io.IOException
import javax.inject.Inject

interface AssetsDataSource {
    fun read() : String
    class Base @Inject constructor(private val context: Context) : AssetsDataSource {
        override fun read(): String {
            return try {
                context.assets.open(COLORS_FILE_NAME).bufferedReader().use { it.readText() }
            } catch (e: IOException) {
                e.message.toString()
            }
        }
        private companion object{
            const val COLORS_FILE_NAME = "github_colors.json"
        }
    }
}