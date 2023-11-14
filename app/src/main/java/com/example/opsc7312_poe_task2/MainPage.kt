package com.example.opsc7312_poe_task2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

// Activity representing the main page of the application
class MainPage : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private val achievements = HomeFragment()
    private val setFragment = Settingsfrag()
    private val birdsfrag = Birdsfrag()
    private val createchecklistsfrag = Createchecklistsfrag()
    private val nearbyfrag = Nearbyfrag()

    // Function called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        // Initialize UI elements
        val btnMap = findViewById<Button>(R.id.btnMap)
        switchFragment(setFragment)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
        val toggleButton: ImageButton = findViewById(R.id.toggle_button)

        // Button click listener to start the MapsActivity
        btnMap.setOnClickListener() {
            startActivity(Intent(this@MainPage, MapsActivity::class.java))
        }

        // Toggle button click listener to open the navigation drawer
        toggleButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Navigation item click listener to switch fragments based on selected item
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_achievements -> {
                    switchFragment(achievements)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_settings -> {
                    switchFragment(setFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_birds -> {
                    switchFragment(birdsfrag)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_createchecklist -> {
                    switchFragment(createchecklistsfrag)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_hotspots -> {
                    switchFragment(nearbyfrag)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }
    }

    // Function to switch fragments in the content layout
    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.contentLayout, fragment)
            .commit()
    }
}
