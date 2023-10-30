package com.markettwits.easygithub.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.markettwits.easygithub.R
import com.markettwits.easygithub.core.AppCompatNavActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatNavActivity() {
    private val viewModel by viewModels<MainViewModel.Base>()
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.initNavigation(
            R.navigation.nav_graph,
            navController(androidx.navigation.fragment.R.id.nav_host_fragment_container)
        )
    }

}