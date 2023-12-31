package com.markettwits.cloud_datasoruce.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryCloud(
    val id: Int,
    val name: String,
    val owner: Owner,
    @SerialName("html_url")
    val htmlUrl: String,
    val description: String? = null,
    val language: String? = null,
    val license: License? = License(""),
    @SerialName("forks_count")
    val forks: Int = 0,
    @SerialName("stargazers_count")
    val stars: Int = 0,
    @SerialName("watchers_count")
    val watchers: Int = 0,
    @SerialName("default_branch")
    val branch: String = "master",
) {
    @Serializable
    data class License(val name: String)

    @Serializable
    data class Owner(@SerialName("login") val name: String)
    @Serializable
    data class Readme(@SerialName ("content") val content : String)
}