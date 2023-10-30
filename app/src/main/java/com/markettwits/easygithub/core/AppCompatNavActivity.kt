package com.markettwits.easygithub.core

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

abstract class AppCompatNavActivity : AppCompatActivity() {
    protected fun navController(navHostResId : Int): NavController {
        val navHostFragment = supportFragmentManager
            .findFragmentById(navHostResId) as NavHostFragment
        return navHostFragment.navController
    }
}