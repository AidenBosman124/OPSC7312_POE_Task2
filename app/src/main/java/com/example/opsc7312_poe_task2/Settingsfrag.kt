package com.example.opsc7312_poe_task2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager.getDefaultSharedPreferences

class Settingsfrag : Fragment() {

    private lateinit var btnKilometres: Button
    private lateinit var btnMiles: Button
    private lateinit var txtMaxDistance: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragment_settingsfrag =
            inflater.inflate(R.layout.fragment_settingsfrag, container, false)

        btnKilometres = fragment_settingsfrag.findViewById(R.id.btnkilometres)
        btnMiles = fragment_settingsfrag.findViewById(R.id.btnmiles)
        txtMaxDistance = fragment_settingsfrag.findViewById(R.id.txtmaxdistance)

        // Set App to kilometres -> 1 Km = 0.621371 Miles
        btnKilometres.setOnClickListener {
            val measurementKm = 1 * 0.621371
            saveDistancePreference(measurementKm)
        }

        // Set App to Miles -> 1 Mile = 1.60934 Km
        btnMiles.setOnClickListener {
            val measurementM = 1 * 1.60934
            saveDistancePreference(measurementM)
        }

        // Handle the maximum distance logic here
        txtMaxDistance.setOnClickListener {
            // You need to handle the logic for maximum distance here
        }

        return fragment_settingsfrag
    }

    private fun saveDistancePreference(distance: Double) {
        val sharedPrefsEditor = getDefaultSharedPreferences(requireContext()).edit()
        sharedPrefsEditor.putInt(getString(R.string.saved_dist_key), distance.toInt())
        sharedPrefsEditor.apply()
    }
}

