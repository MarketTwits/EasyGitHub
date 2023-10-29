package com.markettwits.easygithub.presentation.navigation

import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.navigation.NavController
import com.markettwits.core.navigation.Navigation
import com.markettwits.easygithub.R
import javax.inject.Inject

class BaseNavigation @Inject constructor() : Navigation {
    private lateinit var controller: NavController
    override fun init(@NavigationRes navGraph: Int, startDestination: Int, navHostController: NavController) {
        controller = navHostController
        val current = navHostController.navInflater.inflate(navGraph)
        current.setStartDestination(startDestination)
        navHostController.graph = current
    }

    override fun navigateToRepositoryList() {
        controller.navigate(R.id.to_repositoriesListFragment)
    }

    override fun navigateToAuth() {
        controller.navigate(R.id.to_AuthFragment_with_anim)
    }

    override fun navigateToDetail(owner: String, name: String) {
        val bundle = Bundle()
        bundle.putString("owner", owner)
        bundle.putString("name", name)
        controller.navigate(R.id.to_detailInfoFragment, bundle)
    }

    override fun back() {
        controller.popBackStack()
    }
}