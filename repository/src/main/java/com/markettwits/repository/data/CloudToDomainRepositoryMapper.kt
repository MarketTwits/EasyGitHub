package com.markettwits.repository.data

import com.markettwits.cloud_datasoruce.core.NetworkResult
import com.markettwits.cloud_datasoruce.model.RepositoryCloud
import com.markettwits.repository.domain.RepositoryDomainItem

class CloudToDomainRepositoryMapper : NetworkResult.Mapper<List<RepositoryCloud>, List<RepositoryDomainItem>> {
    override fun map(item: List<RepositoryCloud>): List<RepositoryDomainItem> {
         return item.map {
             RepositoryDomainItem.Success(it.name, it.description ?: "", it.language ?: "")
         }
    }

    override fun map(errorMessage: Int, code: Int): List<RepositoryDomainItem> {
        return listOf(RepositoryDomainItem.Error(errorMessage, code))
    }
}