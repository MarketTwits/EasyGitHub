package com.markettwits.cloud_datasoruce.network

import com.markettwits.cloud_datasoruce.R
import com.markettwits.cloud_datasoruce.model.RepositoryCloud
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    @GET("/user")
    suspend fun auth(
        @Header("Authorization") token : String
    ) : RepositoryCloud.Owner

    @GET("/user/repos")
    suspend fun fetchRepositories(
        @Header("Authorization") token : String,
        @Query("sort") sort: String = SORT_FILTER,
        @Query("per_page") amount: Int = REPOSITORIES_AMOUNT
    ): List<RepositoryCloud>

    @GET("/repos/{owner}/{repo}")
    suspend fun fetchRepository(
        @Header("Authorization") token : String,
        @Path("owner") ownerName: String,
        @Path("repo") repoName: String
    ): RepositoryCloud

    @GET("/repos/{owner}/{repo}/readme")
    suspend fun fetchReadme(
        @Header("Authorization") token : String,
        @Path("owner") ownerName: String,
        @Path("repo") repoName: String
    ) : RepositoryCloud.Readme

    companion object {
         private const val SORT_FILTER = "updated"
         const val WRONG_TOKEN_CODE = 401
         const val NO_RIGHTS_CODE = 403
         const val NOT_FOUND_CODE = 404
         const val NO_INTERNET_CODE = 499
         const val REPOSITORIES_AMOUNT = 10
    }
}