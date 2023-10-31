package com.markettwits.auth.auth_repository


import android.net.http.HttpException
import com.markettwits.auth.data.AuthRepository
import com.markettwits.auth.data.CloudToDomainAuthMapper
import com.markettwits.auth.presentation.AuthUiState
import com.markettwits.cloud_datasoruce.GitHubCloudDataSource
import com.markettwits.cloud_datasoruce.core.HandleNetworkResult
import com.markettwits.cloud_datasoruce.core.HandleRequestCode
import com.markettwits.cloud_datasoruce.model.RepositoryCloud
import com.markettwits.cloud_datasoruce.network.GitHubApiService
import com.markettwits.core.storage.AuthDataSource


import com.markettwits.core.storage.SharedPreferencesStorage
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class AuthRepositoryTest {
    private lateinit var cache: SharedPreferencesStorage
    private lateinit var cloudGitHubDataSource: GitHubCloudDataSource.Auth
    private lateinit var mapper: CloudToDomainAuthMapper
    private lateinit var repository: AuthRepository.All
    private lateinit var localRepository : AuthDataSource
    @Before
    fun init() {
        cache = FakeSharedPreferencesStorage()
        cloudGitHubDataSource = GitHubCloudDataSource.Base(
            FakeApiServiceSuccess(),
            HandleNetworkResult.Base(HandleRequestCode.Base())
        )
        mapper = CloudToDomainAuthMapper()
        repository = AuthRepository.Base(
            cache,
            cloudGitHubDataSource,
            mapper
        )
        localRepository = AuthRepository.BaseLocal(cache)
    }
    @Test
    fun check_check_availability_token(): Unit = runBlocking {
        repository.auth("test_value_token")
        val result = repository.checkAvailabilityToken()
        assertEquals(result, true)

    }
    @Test
    fun check_token_with_cache() : Unit = runBlocking{
        cache.read("key", "token")
        val result = repository.checkToken()
        assertEquals(result, AuthUiState.Success)
    }
    @Test
    fun check_first_auth() : Unit = runBlocking{
        val result = repository.auth("token")
        assertEquals(result, AuthUiState.Success)
    }
    @Test
    fun check_second_auth() : Unit = runBlocking{
        val result = repository.auth("token")
        assertEquals(result, AuthUiState.Success)
        val second = repository.auth()
        assertEquals(second, AuthUiState.Success)
    }
    @Test
    fun check_second_auth_without_cache() : Unit = runBlocking{
        val result = repository.auth("token")
        assertEquals(result, AuthUiState.Success)
        cache.delete("")
        val second = repository.auth()
        assertEquals(second, AuthUiState.Success)
    }
    @Test
    fun check_log_out() = runBlocking {
        cache.save("USER_TOKEN", "token")
        localRepository.logOut()
        val afterClean = cache.read("USER_TOKEN", "DEFAULT_VALUE")
        assertEquals(afterClean, "DEFAULT_VALUE")
    }
    @Test
    fun check_local_token_is_not_avalible() : Unit = runBlocking{
        val result = localRepository.checkAvailabilityToken()
        assertEquals(result, false)
    }
    @Test
    fun check_local_token_is_avalible() : Unit = runBlocking{
        cache.save("USER_TOKEN", "token")
        val result = localRepository.checkAvailabilityToken()
        assertEquals(result, true)
    }
}

class FakeSharedPreferencesStorage : SharedPreferencesStorage {
    private val stringMap: MutableMap<String, String> = mutableMapOf()
    private val defMap: MutableMap<String, Boolean> = mutableMapOf()
    override fun save(key: String, value: Boolean) {
        defMap[key] = value
    }

    override fun save(key: String, value: String) {
        stringMap[key] = value
    }

    override fun read(key: String, default: Boolean): Boolean {
        return defMap[key] ?: default
    }

    override fun read(key: String, default: String): String {
        return stringMap[key] ?: default
    }

    override fun delete(key: String) {
        defMap.remove(key)
        stringMap.remove(key)
    }
}
class FakeApiServiceSuccess : GitHubApiService {
    override suspend fun auth(token: String): RepositoryCloud.Owner =
        RepositoryCloud.Owner("John Tailor")

    override suspend fun fetchRepositories(
        token: String,
        sort: String,
        amount: Int
    ): List<RepositoryCloud> = listOf()

    override suspend fun fetchRepository(
        token: String,
        ownerName: String,
        repoName: String
    ): RepositoryCloud = RepositoryCloud(
        1,
        "",
        RepositoryCloud.Owner("John Tailor"),
        "https://github.com/IacobIonut01/Gallery",
        "lorem description",
        "C#",
        RepositoryCloud.License("Apache 2.0"),
        10,
        2,
        34,
        "master",
    )

    override suspend fun fetchReadme(
        token: String,
        ownerName: String,
        repoName: String
    ): RepositoryCloud.Readme = RepositoryCloud.Readme(
        "IyBGaW50ZWNoVGlua29mZjIwMjMKCiMjINCk0LjQvdGC0LXRhSAyMDIzCiMj\\nINCb0LDQsdC"
    )
}

class FakeApiServiceError : GitHubApiService {
    override suspend fun auth(token: String): RepositoryCloud.Owner =
       throw HttpException("token is not available", Throwable("http"))

    override suspend fun fetchRepositories(
        token: String,
        sort: String,
        amount: Int
    ): List<RepositoryCloud> = throw HttpException("no internet", Throwable("http"))

    override suspend fun fetchRepository(
        token: String,
        ownerName: String,
        repoName: String
    ): RepositoryCloud = throw HttpException("no internet", Throwable("http"))

    override suspend fun fetchReadme(
        token: String,
        ownerName: String,
        repoName: String
    ): RepositoryCloud.Readme = throw HttpException("no internet", Throwable("http"))
}