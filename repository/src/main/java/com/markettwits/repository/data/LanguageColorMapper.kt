package com.markettwits.repository.data

import android.util.Log
import org.intellij.lang.annotations.Language
import org.json.JSONException
import org.json.JSONObject

interface LanguageColorMapper {
    fun map(language: String) : String
    class Base(private val assetsDataSource: AssetsDataSource) : LanguageColorMapper{
        override fun map(language: String): String {
            val colorsString: String = assetsDataSource.read()
            val colors = try {
                JSONObject(colorsString)
            } catch (e: JSONException) {
                return BASE_COLOR
            }
            return if (language.isEmpty()){
                BASE_COLOR
            }else{
               return try {
                   colors.getString(language)
               }catch (e : Exception){
                   BASE_COLOR
               }
            }
        }
        private companion object{
            const val BASE_COLOR = "#FCB32C"
        }
    }
}