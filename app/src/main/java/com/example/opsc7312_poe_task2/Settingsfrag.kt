package com.example.opsc7312_poe_task2

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.HashMap

class Settingsfrag : Fragment() {

    private lateinit var btnKilometres: Button
    private lateinit var btnMiles: Button
    private lateinit var txtMaxDistance: EditText
    val fAuth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    val user = fAuth.currentUser
    private val databaseReference = firestore.collection("users").document(user!!.uid).collection("settings").document("data")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragment_settingsfrag =
            inflater.inflate(R.layout.fragment_settingsfrag, container, false)

        btnKilometres = fragment_settingsfrag.findViewById(R.id.btnkilometres)
        btnMiles = fragment_settingsfrag.findViewById(R.id.btnmiles)
        txtMaxDistance = fragment_settingsfrag.findViewById(R.id.txtmaxdistance)
        setupNumberInputFilter(txtMaxDistance)



        // Initialize Firebase


        // Set App to kilometres -> 1 Km = 0.621371 Miles
        btnKilometres.setOnClickListener {
            val maxdistance = txtMaxDistance.text.toString().toInt()
            val measurementKm = maxdistance * 0.621371
            saveDistancePreference(measurementKm)
            sendToFirebase(measurementKm)
            txtMaxDistance.text.clear()
        }

        // Set App to Miles -> 1 Mile = 1.60934 Km
        btnMiles.setOnClickListener {
            val maxdistance = txtMaxDistance.text.toString().toInt()
            val measurementM = maxdistance * 1.60934
            saveDistancePreference(measurementM)
            sendToFirebase(measurementM)
            txtMaxDistance.text.clear()
        }
        return fragment_settingsfrag
    }

    private fun saveDistancePreference(distance: Double) {
        val sharedPrefsEditor = getDefaultSharedPreferences(requireContext()).edit()
        sharedPrefsEditor.putInt(getString(R.string.saved_dist_key), distance.toInt())
        sharedPrefsEditor.apply()
    }

    private fun sendToFirebase(distance: Double) {
        // Save the distance to Firebase
        val distanceMap = mapOf("distance" to distance)
        databaseReference.set(distanceMap)
    }

    private fun setupNumberInputFilter(editText: EditText) {
        val numberFilter = InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                if (!Character.isDigit(source[i])) {
                    return@InputFilter ""
                }
            }
            null
        }

        editText.filters = arrayOf(numberFilter)
    }
}
