package com.markettwits.repository.data.mapper

import com.markettwits.cloud_datasoruce.core.NetworkResult
import com.markettwits.cloud_datasoruce.model.RepositoryCloud
import com.markettwits.repository.domain.single.ReadmeDomain

class CloudToDomainReadmeMapper : NetworkResult.Mapper<RepositoryCloud.Readme, ReadmeDomain> {
    override fun map(item: RepositoryCloud.Readme): ReadmeDomain {
        return if (item.content.isEmpty()) return ReadmeDomain.Empty()
        else ReadmeDomain.Success(item.content)
    }

    override fun map(errorMessage: Int, code: Int): ReadmeDomain {
        return ReadmeDomain.Empty()
    }
}