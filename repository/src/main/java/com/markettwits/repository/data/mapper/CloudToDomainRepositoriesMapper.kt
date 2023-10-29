package com.markettwits.repository.data.mapper

import com.markettwits.cloud_datasoruce.core.NetworkResult
import com.markettwits.cloud_datasoruce.model.RepositoryCloud
import com.markettwits.repository.domain.list.RepositoriesDomainItem

class CloudToDomainRepositoriesMapper(private val language: LanguageColorMapper) :
    NetworkResult.Mapper<List<RepositoryCloud>, List<RepositoriesDomainItem>> {
    override fun map(item: List<RepositoryCloud>): List<RepositoriesDomainItem> {
        if (item.isEmpty()){
            return listOf(RepositoriesDomainItem.Empty)
        }
        return item.map {
            RepositoriesDomainItem.Success(
                it.name,
                it.owner.name,
                it.description ?: "",
                it.language ?: "",
                language.map(it.language ?: "")
            )
        }
    }

    override fun map(errorMessage: Int, code: Int): List<RepositoriesDomainItem> {
        return listOf(RepositoriesDomainItem.Error(errorMessage, code))
    }
}