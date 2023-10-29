package com.markettwits.repository.domain.single

import com.markettwits.core.R
import com.markettwits.repository.presentation.detail.RepositoryUiState
import com.markettwits.repository.presentation.list.RepositoriesUiState

interface DomainToUiRepositoryMapper {
    fun map(item : RepositoryDomainItem.Base) : RepositoryUiState
    fun map(code : Int, message : Int) : RepositoryUiState
    class Base : DomainToUiRepositoryMapper {

        override fun map(item: RepositoryDomainItem.Base): RepositoryUiState {
            return RepositoryUiState.Success(
                name = item.name,
                htmlUrl = item.htmlUrl,
                license = item.license,
                forks = item.forks,
                stars = item.stars,
                watchers = item.watchers
            )
        }

        override fun map(code: Int, message : Int) : RepositoryUiState {
            val icon = when(code){
                499 -> R.drawable.ic_network_error
                else -> R.drawable.ic_something_error
            }
            return RepositoryUiState.Error(icon, message, message)
        }
    }
}
