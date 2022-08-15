package com.example.snake2

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.snake2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    val APP_PREFERENCES = "settings"

    val APP_PREFERENCES_UNCENSOREDMOD = "uncensoredMod"
    val APP_PREFERENCES_GODMOD = "godMod"

    var APP_PREFERENCES_GAMEMOD_VALUE = false
    var APP_PREFERENCES_GODMOD_VALUE = false
    var APP_PREFERENCES_SNAKEBODY_VALUE = 0
    var APP_PREFERENCES_CHERRY_VALUE = 0

    private val mainSettings: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        defaultOnCreate()
    }

    private fun defaultOnCreate() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

/*
    override fun onPause() {
        super.onPause()
        // Запоминаем данные
        val editor = mainSettings!!.edit()
        editor.putBoolean(APP_PREFERENCES_UNCENSOREDMOD, APP_PREFERENCES_GAMEMOD_VALUE)
        editor.putBoolean(APP_PREFERENCES_GODMOD, APP_PREFERENCES_GODMOD_VALUE)
        editor.putInt(resources.getString(R.string.pref_snakeBody), APP_PREFERENCES_SNAKEBODY_VALUE)
        editor.putInt(resources.getString(R.string.pref_cherries), APP_PREFERENCES_SNAKEBODY_VALUE)
        editor.apply()
    }

    override fun onResume() {
        super.onResume()
        val s = "0"
        if (mainSettings!!.contains(APP_PREFERENCES_UNCENSOREDMOD)) APP_PREFERENCES_GAMEMOD_VALUE =
            mainSettings.getBoolean(APP_PREFERENCES_UNCENSOREDMOD, false)
        if (mainSettings.contains(APP_PREFERENCES_GODMOD)) APP_PREFERENCES_GODMOD_VALUE =
            mainSettings.getBoolean(APP_PREFERENCES_GODMOD, false)
        if (mainSettings.contains(resources.getString(R.string.pref_snakeBody))) APP_PREFERENCES_SNAKEBODY_VALUE =
            mainSettings.getInt(resources.getString(R.string.pref_snakeBody), 0)
        if (mainSettings.contains(resources.getString(R.string.pref_cherries))) APP_PREFERENCES_CHERRY_VALUE =
            mainSettings.getInt(resources.getString(R.string.pref_cherries), 0)
    }*/
}