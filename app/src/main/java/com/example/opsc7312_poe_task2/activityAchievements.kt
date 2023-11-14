package com.example.opsc7312_poe_task2

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.MotionEffect
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class activityAchievements : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AchievementsAdapter
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievements)

        recyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val dividerItemDecoration =
            DividerItemDecoration(recyclerView.context, layoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

        val achievements = HelperClass.AchievementManager.getAllAchievements()
        Log.d("AchievementsAdapter", "Data size: ${achievements.size}")
        adapter = AchievementsAdapter(achievements)
        recyclerView.adapter = adapter

        fetchAchievements()
        adapter.notifyDataSetChanged()

        val buttonTriggerActions = findViewById<Button>(R.id.button)
        buttonTriggerActions.setOnClickListener {
            //achievementFirestore()
            // Testing scenario
            //HelperClass.AchievementManager.trackDistanceTraveled(5.0) // Assume the condition is 5.0 km
            //HelperClass.AchievementManager.trackBirdsAdded(5) // Assume the condition is 5 birds
            //HelperClass.AchievementManager.trackMarkerPlaced()
            // Update the RecyclerView with the new data
        }

        drawerLayout = findViewById(R.id.drawer_layout)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.open_nav, R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setCheckedItem(R.id.nav_home)
    }

    private fun achievementFirestore(id: String, status: Boolean) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid
        Log.d("User", "user: $currentUser")

        if (userId != null) {
            val db = FirebaseFirestore.getInstance() // Change this line

            for (achievement in HelperClass.AchievementManager.achievementList) {
                val achievementData = hashMapOf(
                    "id" to achievement.id,
                    "isUnlocked" to achievement.isUnlocked,
                    "user" to currentUser.uid
                )
                Log.d("Achievements id:", achievement.id.toString())
                db.collection("Achievements")
                    .add(achievementData)
                    .addOnSuccessListener { documentReference ->
                        // Document added successfully
                        Log.d(MotionEffect.TAG, "data saved:success")
                        val alertDialog = AlertDialog.Builder(this)
                        alertDialog.setPositiveButton("OK") { dialog, _ ->
                            // when the user clicks OK
                            dialog.dismiss()
                        }
                        alertDialog.show()
                    }
                    .addOnFailureListener { e ->
                        // Handle errors
                        Log.d(MotionEffect.TAG, e.message.toString())
                        Log.d(MotionEffect.TAG, "data saved:failure")
                        val alertDialog = AlertDialog.Builder(this)
                        alertDialog.setTitle("unsuccessfully Saved")
                        alertDialog.setMessage("Achievement Not Saved")
                        alertDialog.setPositiveButton("OK") { dialog, _ ->
                            // when the user clicks OK
                            dialog.dismiss()
                        }
                        alertDialog.show()
                    }
            }
        }
    }

    private fun fetchAchievements() {
        try {
            // Reference to the Firestore collection
            val db = FirebaseFirestore.getInstance()
            val collectionRef = db.collection("Achievements")

            val currentUser = FirebaseAuth.getInstance().currentUser
            val userId = currentUser?.uid

            if (userId != null) {
                Log.d("ContentValues", "fetchAchievement: userID $userId")
            } else {
                Log.d("ContentValues", "fetchAchievement: User is not authenticated")
            }

            // Query the collection based on the "user" field
            collectionRef.whereEqualTo("user", userId)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val fetchedAchievements = mutableListOf<HelperClass.Achievement>()

                    for (doc in querySnapshot) {
                        // doc.data contains the document data
                        val achievementID = doc.getString("id")
                        val achievementStatus = doc.getBoolean("isUnlocked")

                        Log.d("ContentValues", "$achievementID $achievementStatus")

                        // Update the corresponding achievement in the list
                        if (achievementID != null && achievementStatus != null) {
                            HelperClass.AchievementManager.achievementList =
                                updateAchievementStatus(achievementID, achievementStatus)

                            // Notify the adapter that the data has changed
                            adapter.updateData(HelperClass.AchievementManager.achievementList)
                        }
                    }

                    // the fetched achievements in the AchievementManager
                    HelperClass.AchievementManager.fetchedAchievements = fetchedAchievements
                }
                .addOnFailureListener { e ->
                    // Handle errors
                    Log.d(MotionEffect.TAG, "fetchAchievements: failure " + e.message.toString())
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // only needs to work in this class
    // just for changing the appearance of the achievements
    private fun updateAchievementStatus(
        achievementID: String,
        isUnlocked: Boolean
    ): List<HelperClass.Achievement> {
        return HelperClass.AchievementManager.achievementList.map { achievement ->
            if (achievement.id == achievementID) {
                achievement.copy(isUnlocked = isUnlocked)
            } else {
                achievement
            }
        }
    }

    private fun signOutUser() {
        val auth = FirebaseAuth.getInstance()
        auth.signOut()
        Toast.makeText(this, "Logged Out!", Toast.LENGTH_SHORT).show()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                val currentActivity = this::class.java

                // check what's the current activity, if not the main activity it will start the main activity
                if (currentActivity == activityAchievements::class.java) {
                    startActivity(Intent(this, MainPage::class.java))
                }
            }
        }
        return true
    }
}
