package com.gsrikar.wikipediasearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Enable Night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        setUpActionbar()
    }

    private fun setUpActionbar() {
        NavigationUI.setupActionBarWithNavController(
            this,
            findNavController(R.id.fragment)
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragment).navigateUp()
    }

}
