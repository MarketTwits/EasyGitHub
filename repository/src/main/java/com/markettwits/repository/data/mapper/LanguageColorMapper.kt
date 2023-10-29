package com.markettwits.repository.data.mapper

import com.markettwits.repository.data.AssetsDataSource
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

interface LanguageColorMapper {
    fun map(language: String) : String
    class Base @Inject constructor(private val assetsDataSource: AssetsDataSource) :
        LanguageColorMapper {
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