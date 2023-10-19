package com.example.opsc7312_poe_task2


import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.opsc7312_poe_task2.R
import com.example.opsc7312_poe_task2.SharedPreferencesManager
import com.google.android.material.navigation.NavigationView

class Settingsfrag : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var decisionSwitch: Switch
    private lateinit var maxDistanceEditText: EditText
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private var maxDistance = 20

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_settingsfrag, container, false)

        sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        sharedPreferencesManager = SharedPreferencesManager(requireContext()) // Initialize SharedPreferencesManager

        decisionSwitch = fragmentView.findViewById(R.id.decisionSwitch)
        maxDistanceEditText = fragmentView.findViewById(R.id.MaxDistanceEditText)
        val applyButton = fragmentView.findViewById<Button>(R.id.apply_button)

        val isImperialEnabled = sharedPreferencesManager.getUnit() // Use the SharedPreferencesManager

        // Set the initial state of the switch
        decisionSwitch.isChecked = isImperialEnabled

        // Handle switch state changes
        decisionSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Save the user's preference when the switch state changes
            sharedPreferencesManager.setUnit(isChecked)
        }

        // Check if imperial
        var maxInt = 500
        if (sharedPreferencesManager.getUnit() == true) {
            maxInt = 310
        }

        maxDistanceEditText.setText(maxDistance.toString())

        applyButton.setOnClickListener {
            val maxDistanceString = maxDistanceEditText.text.toString()
            if (maxDistanceString.isNotBlank() && maxDistanceString.toInt() <= maxInt) {
                try {
                    maxDistance = maxDistanceString.toInt()
                    sharedPreferencesManager.setMaxDistance(maxDistance)
                    Toast.makeText(requireContext(), "Max distance saved: $maxDistance", Toast.LENGTH_SHORT).show()
                } catch (e: NumberFormatException) {
                    Toast.makeText(requireContext(), "Invalid max distance", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please enter max distance that's less than 500km/310mi", Toast.LENGTH_SHORT).show()
            }
            Log.d(TAG, "onCreate: MAX DISTANCE IS ${sharedPreferencesManager.getMaxDistance()}")
        }

        return fragmentView
    }
}
