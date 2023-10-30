package com.markettwits.easygithub.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.markettwits.easygithub.R
import com.markettwits.easygithub.data.ConnectivityObserver
import com.markettwits.easygithub.data.NetworkConnectivityObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel.Base>()
    private lateinit var observer: ConnectivityObserver
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observer = NetworkConnectivityObserver(applicationContext)

        observer.observe().onEach {
            //showSnackbar(it.toString())
           // Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_LONG).show()
        }.launchIn(lifecycleScope)

        viewModel.initNavigation(R.navigation.nav_graph, getNavigationController())
    }

    private fun getNavigationController(): NavController {
        val navHostFragment = supportFragmentManager
            .findFragmentById(androidx.navigation.fragment.R.id.nav_host_fragment_container) as NavHostFragment
        return navHostFragment.navController
    }


    private fun showSnackbar(state: String) {
        val view = this.findViewById<View>(android.R.id.content)
        Snackbar.make(view, state, Snackbar.LENGTH_LONG).show()
    }
}