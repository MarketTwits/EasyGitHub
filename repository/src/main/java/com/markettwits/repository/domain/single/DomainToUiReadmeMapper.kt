package com.markettwits.repository.domain.single

import android.util.Base64
import com.markettwits.core.R
import com.markettwits.repository.presentation.detail.RepositoryReadmeUiState


interface DomainToUiReadmeMapper {
    fun map(value: String) : RepositoryReadmeUiState
    fun map(value : Int) : RepositoryReadmeUiState

    class Base(private val parse : UTF8Parse) : DomainToUiReadmeMapper{
        override fun map(value: String) = RepositoryReadmeUiState.Success(parse.map(value))
        override fun map(value: Int) = RepositoryReadmeUiState.Empty(R.string.empty_readme)

    }
}
interface UTF8Parse{
    fun decode(string: String) : ByteArray
    fun map(value : String) : String
    class Base() : UTF8Parse{
        override fun decode(string: String): ByteArray =
             Base64.decode(string, Base64.DEFAULT)
        override fun map(value: String) =
            String(decode(value), charset(UTF8))
        private companion object{
            const val UTF8 = "UTF-8"
        }

    }
}