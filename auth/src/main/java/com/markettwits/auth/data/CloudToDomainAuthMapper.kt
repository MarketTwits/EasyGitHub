package com.markettwits.auth.data

import com.markettwits.auth.presentation.AuthUiState
import com.markettwits.cloud_datasoruce.core.NetworkResult
import com.markettwits.cloud_datasoruce.model.RepositoryCloud

class CloudToDomainAuthMapper : NetworkResult.Mapper<RepositoryCloud.Owner, AuthUiState> {
    override fun map(item: RepositoryCloud.Owner) = AuthUiState.Success
    override fun map(errorMessage: Int, code: Int) = AuthUiState.Error(errorMessage)
}