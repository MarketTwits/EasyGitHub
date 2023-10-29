package com.markettwits.easygithub.presentation.navigation

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.IntegerRes
import androidx.annotation.NavigationRes
import androidx.navigation.NavController
import com.markettwits.auth.presentation.AuthFragmentDirections
import com.markettwits.core.navigation.Navigation
import com.markettwits.easygithub.R
import com.markettwits.repository.presentation.list.RepositoriesListFragmentDirections
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
        val action = AuthFragmentDirections.toRepositoriesListFragment()
        controller.navigate(action)
    }

    override fun navigateToAuth() {
        controller.navigate(R.id.to_AuthFragment_with_anim)
    }

    override fun navigateToDetail(owner: String, name: String) {
        //todo try replacy save args and directions to default realisation
        val action = RepositoriesListFragmentDirections.toDetailInfoFragment(owner, name)
//        val bundle = Bundle()
//        bundle.putString("owner", owner)
//        bundle.putString("name", name)
//        controller.navigate(R.id.to_detailInfoFragment, bundle)
        controller.navigate(action)
    }

    override fun back() {
        controller.popBackStack()
    }
}