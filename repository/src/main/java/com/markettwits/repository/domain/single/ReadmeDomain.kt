package com.markettwits.repository.domain.single

import com.markettwits.core.R
import com.markettwits.repository.presentation.detail.RepositoryReadmeUiState

interface ReadmeDomain {
    fun map(mapper: DomainToUiReadmeMapper) : RepositoryReadmeUiState

    class Success(private val value : String) : ReadmeDomain {
        override fun map(mapper: DomainToUiReadmeMapper) =
            mapper.map(value)

    }

    class Empty(private val message : Int = R.string.empty_readme) : ReadmeDomain {
        override fun map(mapper: DomainToUiReadmeMapper) =
            mapper.map(message)

    }
}