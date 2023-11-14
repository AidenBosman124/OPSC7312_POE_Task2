package com.example.opsc7312_poe_task2

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.constraintlayout.helper.widget.MotionEffect
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// Helper class with various utilities and data structures
class HelperClass {

    // Data class representing Bird information
    data class Bird(val name: String, val dateTime: String, val location: String)

    // HashMap to store Bird information mapped to user names
    public val BirdMap: HashMap<String, Bird> = HashMap()

    // Function to add Bird information to the list
    fun addToList(usersName: String, name: String, dateTime: String, location: String) {
        val bird = Bird(name, dateTime, location)
        BirdMap.put(usersName, bird)
        Log.d("HelperClass", "Achievement ID: $bird")
    }

    // Data class representing Achievement information
    data class Achievement(
        val id: String,
        val name: String,
        val description: String,
        val conditions: List<Condition>,
        var isUnlocked: Boolean = false
    )

    // Data class representing Achievement condition
    data class Condition(
        val type: ConditionType,
        val value: Any
    )

    // Enum class representing Achievement condition types
    enum class ConditionType {
        DISTANCE_TRAVELED,
        BIRDS_ADDED,
        MARKER_PLACED,
        LOGGED_IN,
        SETTINGS_CHANGE
    }

    // Data class representing Achievement progress
    private data class AchievementProgress(
        var isUnlocked: Boolean = false
        // Add other progress-related properties if needed
    )

    // List to store Achievements
    val achievementList: MutableList<Achievement> = mutableListOf()

    // Object to manage Achievements
    object AchievementManager {
        var fetchedAchievements: List<Achievement> = emptyList()

        private val unlockedAchievements = mutableListOf<Achievement>()

        private val unlockedAchievementsMap = mutableMapOf<String, AchievementProgress>()

        // Define a list of achievements
        // (the number at value is the condition that must be met)
        var achievementList = listOf(
            Achievement("Bronze-Travel", "Junior Traveler", "Achieve 5km of travel", listOf(Condition(ConditionType.DISTANCE_TRAVELED, 5.0))),
            Achievement("Silver-Travel", "Experienced Traveler", "Achieve 10km of travel", listOf(Condition(ConditionType.DISTANCE_TRAVELED, 10.0))),
            Achievement("Gold-Travel", "Pro Traveler", "Achieve 15km of travel", listOf(Condition(ConditionType.DISTANCE_TRAVELED, 15.0))),
            Achievement("Bronze-Birds", "Junior Observer", "Observe 5 birds", listOf(Condition(ConditionType.BIRDS_ADDED, 5))),
            Achievement("Silver-Birds", "Experienced Observer", "Observe 10 birds", listOf(Condition(ConditionType.BIRDS_ADDED, 10))),
            Achievement("Gold-Birds", "Pro Observer", "Observe 15 birds", listOf(Condition(ConditionType.BIRDS_ADDED, 15))),
            Achievement("marker_placed", "Explorer", "Place your first marker on the map", listOf(Condition(ConditionType.MARKER_PLACED, 1))),
            Achievement("settings_changed", "Mechanic", "Change the settings to your preferences", listOf(Condition(ConditionType.SETTINGS_CHANGE, 1))),
            Achievement("login_first", "Welcome Aboards", "Logged in for the first time", listOf(Condition(ConditionType.LOGGED_IN, 1)))
            // Add other achievements as needed
        )

        // Function to track achievements based on distance traveled
        private fun trackAchievements(achievementId: String, type: ConditionType, userValue: Double) {
            val achievement = achievementList.find { it.id == achievementId } ?: return
            val achievementProgress = unlockedAchievementsMap.getOrPut(achievementId) { AchievementProgress() }

            if (!achievementProgress.isUnlocked && meetsCondition(achievement, type, userValue)) {
                newUnlockAchievement(achievementId, achievement)
                achievementProgress.isUnlocked = true
            }
        }

        // Function to track achievements based on type and user value
        private fun trackAchievements(type: ConditionType, userValue: Number) {
            achievementList.forEach { achievement ->
                if (!achievement.isUnlocked && meetsCondition(achievement, type, userValue.toDouble())) {
                    unlockAchievement(achievement)
                }
            }
        }

        // Function to unlock a new achievement
        private fun newUnlockAchievement(achievementId: String, achievement: Achievement) {
            achievement.isUnlocked = true
            // Logic to handle unlocking achievement

            // If I understand correctly, this should save the data to the Firebase database
            achievementFirestore(achievementId, achievement.isUnlocked)
            println("Achievement Unlocked: $achievementId")
        }

        // Function to check if a condition is met for an achievement
        private fun meetsCondition(achievement: Achievement, type: ConditionType, userValue: Double): Boolean {
            val condition = achievement.conditions.find { it.type == type } ?: return false
            return userValue >= when (condition.value) {
                is Int -> (condition.value as Int).toDouble()
                is Double -> condition.value as Double
                else -> throw IllegalArgumentException("Unsupported condition value type")
            }
        }

        // Function to track distance traveled and unlock relevant achievements
        fun trackDistanceTraveled(distance: Double) {
            checkAndUnlockAchievements(ConditionType.DISTANCE_TRAVELED, distance)
            trackAchievements(ConditionType.DISTANCE_TRAVELED, distance)
        }

        // Function to track birds added and unlock relevant achievements
        fun trackBirdsAdded(count: Int, context: Context) {
            checkAndUnlockAchievements(ConditionType.BIRDS_ADDED, count)
            trackAchievements(ConditionType.BIRDS_ADDED, count)
            Log.d("HelperClass", "Achievement ID: birdcount")
            showNotification(context, "Achievement Unlocked!", "You have tracked quite a few birds!")
        }

        // Function to track placing a marker and unlock relevant achievements
        fun trackMarkerPlaced() {
            checkAndUnlockAchievements(ConditionType.MARKER_PLACED, 1)
            trackAchievements(ConditionType.MARKER_PLACED, 1)
        }

        // Function to track first login and unlock relevant achievements
        fun trackLoginFirst(context: Context) {
            checkAndUnlockAchievements(ConditionType.LOGGED_IN, 1)
            trackAchievements(ConditionType.LOGGED_IN, 1)
            Log.d("HelperClass", "Achievement ID: login")
            showNotification(context, "Achievement Unlocked!", "You have logged in for the first time!")
        }

        // Function to track settings change and unlock relevant achievements
        fun trackSettingsChanged(context: Context) {
            checkAndUnlockAchievements(ConditionType.SETTINGS_CHANGE, 1)
            trackAchievements(ConditionType.SETTINGS_CHANGE, 1)
            Log.d("HelperClass", "Achievement ID: settingschanged")
            showNotification(context, "Achievement Unlocked!", "You have changed your settings! Keep on watching those birds!")
        }

        // Function to save achievement data to Firebase Firestore
        private fun achievementFirestore(id: String, status: Boolean) {
            val currentUser = FirebaseAuth.getInstance().currentUser
            val userId = currentUser?.uid
            Log.d("User", "user: ${currentUser}")

            if (userId != null) {
                val db = FirebaseFirestore.getInstance()

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

                            //------------------had to comment out the alertdialog cos it cant function outside of an activity

                            //val alertDialog = AlertDialog.Builder(this)
                            //alertDialog.setTitle("Successfully Saved")
                            //alertDialog.setMessage("Achievement Saved")
                            //alertDialog.setPositiveButton("OK") { dialog, _ ->
                            // when the user clicks OK
                            //    dialog.dismiss()
                            //}
                            //alertDialog.show()
                        }
                        .addOnFailureListener { e ->
                            // Handle errors
                            Log.d(MotionEffect.TAG, e.message.toString())
                            Log.d(MotionEffect.TAG, "data saved:failure")
                            //val alertDialog = AlertDialog.Builder(this)
                            //alertDialog.setTitle("unsuccessfully Saved")
                            //alertDialog.setMessage("Achievement Not Saved")
                            //alertDialog.setPositiveButton("OK") { dialog, _ ->
                            // when the user clicks OK
                            //    dialog.dismiss()
                            //}
                            //alertDialog.show()
                        }
                }
            }
        }

        //-------------------------------------------------------------------------------------------------old stuff
        // Function to check and unlock achievements based on type and value
        private fun checkAndUnlockAchievements(type: ConditionType, value: Number) {
            val relevantAchievements = achievementList.filter { it.conditions.any { it.type == type } }

            relevantAchievements.forEach { achievement ->
                achievement.conditions.filter { it.type == type }.forEach { condition ->
                    val conditionValue = when (condition.value) {
                        is Int -> (condition.value as Int).toDouble()
                        is Double -> condition.value as Double
                        else -> throw IllegalArgumentException("Unsupported condition value type")
                    }

                    val userValue = value.toDouble()

                    if (userValue >= conditionValue) {
                        unlockAchievement(achievement)
                    }
                }
            }
        }

        // Function to unlock an achievement
        private fun unlockAchievement(achievement: Achievement) {
            if (!unlockedAchievements.contains(achievement)) {
                unlockedAchievements.add(achievement)
                // You can notify the user, update UI, or store the achievement.
                achievement.isUnlocked = true
                println("Achievement Unlocked: ${achievement.name}")
            }
        }

        // Function to get a list of unlocked achievements
        fun getUnlockedAchievements(): List<Achievement> {
            return unlockedAchievements.toList()
        }

        // Function to get a list of all achievements
        fun getAllAchievements(): List<Achievement> {
            return achievementList.toList()
        }

        // Function to show a notification
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        private fun showNotification(context: Context, title: String?, message: String?) {
            val notificationHelper = NotificationHelper(context)
            notificationHelper.showNotification(title, message)
        }
    }
}
