package com.markettwits.repository.data.mapper

import com.markettwits.cloud_datasoruce.core.NetworkResult
import com.markettwits.cloud_datasoruce.model.RepositoryCloud
import com.markettwits.repository.domain.single.RepositoryDomainItem

class CloudToDomainRepositoryMapper : NetworkResult.Mapper<RepositoryCloud, RepositoryDomainItem>{
    override fun map(item: RepositoryCloud): RepositoryDomainItem {
        return RepositoryDomainItem.Base(
            id = item.id,
            name = item.name,
            htmlUrl = item.htmlUrl,
            license = mapLicence(item.license),
            forks = item.forks,
            stars = item.stars,
            watchers = item.watchers,
        )
    }
    fun mapLicence(license: RepositoryCloud.License?) : String{
        return license?.name ?: RepositoryCloud.License("").name
    }
    override fun map(errorMessage: Int, code: Int): RepositoryDomainItem {
        return RepositoryDomainItem.Error(errorMessage, code)
    }
}