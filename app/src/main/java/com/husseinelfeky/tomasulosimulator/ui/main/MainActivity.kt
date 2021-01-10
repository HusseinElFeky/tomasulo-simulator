package com.husseinelfeky.tomasulosimulator.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.husseinelfeky.tomasulosimulator.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNavController()
        initToolbar()
    }

    private fun initNavController() {
        navController = (supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment).navController
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        NavigationUI.setupWithNavController(toolbar, navController)
    }
}
