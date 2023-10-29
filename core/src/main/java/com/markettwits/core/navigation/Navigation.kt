package com.markettwits.core.navigation

import androidx.annotation.IntegerRes
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