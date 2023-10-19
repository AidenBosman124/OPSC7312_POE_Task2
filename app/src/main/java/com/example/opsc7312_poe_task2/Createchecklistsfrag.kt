package com.example.opsc7312_poe_task2

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.opsc7312_poe_task2.databinding.FragmentCreatechecklistsfragBinding
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import java.text.SimpleDateFormat
import java.util.*

class Createchecklistsfrag : Fragment() {
    private var binding: FragmentCreatechecklistsfragBinding? = null
    private var currentLocation: Location? = null
    private var locationManager: LocationManager? = null
    private val locationPermissionCode = 123

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatechecklistsfragBinding.inflate(inflater, container, false)
        val createchecklistsfrag = binding?.root

        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Request location permissions if not already granted
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        } else {
            // Location permission is granted, request location updates
            try {
                locationManager?.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1000, 10f,
                    object : LocationListener {
                        override fun onLocationChanged(location: Location) {
                            currentLocation = location
                        }

                        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                            // Handle status changes if needed
                        }

                        override fun onProviderEnabled(provider: String) {
                            // Handle provider enabled event if needed
                        }

                        override fun onProviderDisabled(provider: String) {
                            // Handle provider disabled event if needed
                        }
                    }
                )
            } catch (e: SecurityException) {
                e.printStackTrace()
            }

            // Set a click listener for your "Create" button
            val button4 = createchecklistsfrag?.findViewById<Button>(R.id.button4)
            button4?.setOnClickListener {
                val name = binding?.editTextText2?.text.toString()
                val date = binding?.editTextText4?.text.toString()
                val location = binding?.editTextText3?.text.toString()

                // Handle your button click here
                // You can use the name, date, and location as needed.
                // For example, you can display the values in a toast message:
                val message = "Name: $name\nDate: $date\nLocation: $location\nCurrent Location: Lat: ${
                    currentLocation?.latitude ?: 0.0
                }, Long: ${currentLocation?.longitude ?: 0.0}"
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

                // Call the saveEntry function with the provided data
                saveEntry(name, location, date)
            }

            // Set click listeners for the "Add" and "Refresh" buttons
            val btnAdd = createchecklistsfrag?.findViewById<Button>(R.id.btnAdd)
            val btnRefresh = createchecklistsfrag?.findViewById<Button>(R.id.btnRefresh)

            btnAdd?.setOnClickListener {
                // Handle the "Add" button click here
                btnAddClick()
            }

            btnRefresh?.setOnClickListener {
                // Handle the "Refresh" button click here
                btnRefreshClick()
            }
        }

        return createchecklistsfrag
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun saveEntry(name: String, location: String, date: String) {
        // Implement your logic to save the entry here
        // This is where you can save the data to a database or perform other actions.
        // For example, you can display a toast message:
        val message = "Saved!\nName: $name\nDate: $date\nLocation: $location"
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

        // You can save the data to a database or perform other actions here.
        // For example:
        // YourDatabaseClass.saveEntry(name, location, date)
    }

    private fun btnAddClick() {
        val name = binding?.editTextText2?.text.toString()
        val date = binding?.editTextText4?.text.toString()
        val location = binding?.editTextText3?.text.toString()

        // Check if any of the fields are empty
        if (name.isBlank() || date.isBlank() || location.isBlank()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // You can perform an action when the "Add" button is clicked.
        // For example, you can add the checklist/observation to a list or database.

        // Here's a sample action:
        val newEntry = "Name: $name, Date: $date, Location: $location"
        // Store or display the newEntry as needed

        // Clear the input fields
        binding?.editTextText2?.text?.clear()
        binding?.editTextText4?.text?.clear()
        binding?.editTextText3?.text?.clear()

        Toast.makeText(requireContext(), "Added: $newEntry", Toast.LENGTH_SHORT).show()
    }

    private fun btnRefreshClick() {
        // Handle the "Refresh" button click.
        // You can implement a refresh action here.
        // For example, if you have a list of checklists/observations, you can reload or refresh the list.
        // You can also update any other UI components or data that needs to be refreshed.

        Toast.makeText(requireContext(), "Refreshed", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val locationPermissionCode = 123
    }
}
