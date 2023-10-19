package com.example.opsc7312_poe_task2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.location.Location
import android.location.Geocoder
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.opsc7312_poe_task2.databinding.FragmentCreatechecklistsfragBinding
import java.text.SimpleDateFormat
import java.util.*

class Createchecklistsfrag : Fragment() {
    private var binding: FragmentCreatechecklistsfragBinding? = null
    private lateinit var currentLocation: Location

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatechecklistsfragBinding.inflate(inflater, container, false)
        val createchecklistsfrag = binding?.root

        // Request location permissions if not already granted
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        } else {
            // Location permission is granted, initialize location service
            // This is where you would set up the location service and get the user's location
            // For this example, let's assume you have a function called 'getUserLocation' to get the location.
            currentLocation = getUserLocation()

            // Set a click listener for your button
            binding?.button4?.setOnClickListener {
                val name = binding?.editTextText2?.text.toString()
                val date = binding?.editTextText4?.text.toString()
                val location = binding?.editTextText3?.text.toString()

                // Handle your button click here
                // You can use the name, date, and location as needed.
                // For example, you can display the values in a toast message:
                val message = "Name: $name\nDate: $date\nLocation: $location\nCurrent Location: Lat: ${currentLocation.latitude}, Long: ${currentLocation.longitude}"
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }

        return createchecklistsfrag
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun getUserLocation(): Location {
        // In a real app, you would use location services or the device's GPS to get the user's location.
        // For the sake of this example, we'll create a dummy location.
        val location = Location("dummy")
        location.latitude = 40.7128 // Example latitude
        location.longitude = -74.0060 // Example longitude
        return location
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 123
    }
}
