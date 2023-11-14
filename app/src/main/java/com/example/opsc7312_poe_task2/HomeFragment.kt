package com.example.opsc7312_poe_task2

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val listButton = view.findViewById<Button>(R.id.btnList)
        val mapButton = view.findViewById<Button>(R.id.btnMap)
        val achievementButton = view.findViewById<Button>(R.id.btnAchievements)

        listButton.setOnClickListener {
            btnListClick()
        }

        mapButton.setOnClickListener {
            btnMapClick()
        }

        achievementButton.setOnClickListener {
            btnAchievementClick()
        }

        checkAchievements() // Check achievements when the fragment is created

        return view
    }

    private fun btnListClick() {
        startActivity(Intent(requireContext(), Nearbyfrag::class.java))
    }

    private fun btnMapClick() {
        startActivity(Intent(requireContext(), MapsActivity::class.java))
    }

    private fun btnAchievementClick() {
        startActivity(Intent(requireContext(), activityAchievements::class.java))
    }

    private fun checkAchievements() {
        val bronzeAchievementCount = 5
        val silverAchievementCount = 10
        val goldAchievementCount = 25

        val userAchievementCount = HelperClass.AchievementManager.achievementList.count { it.isUnlocked }

        if (userAchievementCount >= goldAchievementCount) {
            // User has achieved gold status
            // You can perform additional actions or show a message here
        } else if (userAchievementCount >= silverAchievementCount) {
            // User has achieved silver status
            // You can perform additional actions or show a message here
        } else if (userAchievementCount >= bronzeAchievementCount) {
            // User has achieved bronze status
            // You can perform additional actions or show a message here
        } else {
            // User has not achieved any special status yet
        }
    }
}
