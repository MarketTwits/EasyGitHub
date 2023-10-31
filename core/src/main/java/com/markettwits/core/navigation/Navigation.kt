package com.markettwits.core.navigation

import androidx.annotation.NavigationRes
import androidx.navigation.NavController

interface Navigation {
    /**
     * Initializes the navigation graph for this class. This method should be called in the activity's `onCreate` method.
     *
     * @param navGraph The resource ID of the navigation graph to be used.
     * @param startDestination The resource ID of the starting destination within the navigation graph.
     * @param navHostController The NavController to be used for navigation.
     */
    fun init(@NavigationRes navGraph : Int, startDestination : Int, navHostController: NavController)
    fun navigateToRepositoryList()
    fun navigateToAuth()
    fun navigateToDetail(owner : String, name : String)
    fun back()
}
abstract class FakeNavigation : Navigation{
    private val map: MutableList<String>  = mutableListOf()
    override fun init(navGraph: Int, startDestination: Int, navHostController: NavController) =Unit

    override fun navigateToRepositoryList() {
        map.add(REPOSITORY_SCREEN)
    }

    override fun navigateToAuth() {
        map.add(AUTH_SCREEN)
    }

    override fun navigateToDetail(owner: String, name: String) {
       map.add(DETAIL_SCREEN)
    }
    override fun back() {
       map.removeLast()
    }
    companion object{
        const val REPOSITORY_SCREEN = "repositoryScreen"
        const val AUTH_SCREEN = "authScreen"
        const val DETAIL_SCREEN = "detailScreen"
    }
    class Base : FakeNavigation()
}