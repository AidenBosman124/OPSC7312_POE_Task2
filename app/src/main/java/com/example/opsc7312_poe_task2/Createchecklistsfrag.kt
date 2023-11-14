package com.example.opsc7312_poe_task2

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.opsc7312_poe_task2.databinding.FragmentCreatechecklistsfragBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.*

class Createchecklistsfrag : Fragment() {
    private var binding: FragmentCreatechecklistsfragBinding? = null
    private var currentLocation: Location? = null
    private var locationManager: LocationManager? = null
    private val locationPermissionCode = 123
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var textView_longitude: TextView
    private lateinit var textView_latitude: TextView




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatechecklistsfragBinding.inflate(inflater, container, false)
        val createchecklistsfrag = binding?.root

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        textView_longitude= binding?.textViewLongitude!!
        textView_latitude= binding?.textViewLatitude!!

        getCurrentLocation();

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
                val longitude = currentLocation?.longitude ?:0.0
                val latitude = currentLocation?.latitude ?:0.0

                // Display latitude and longitude in TextViews
                textView_latitude.text = "Latitude: $latitude"
                textView_longitude.text = "Longitude: $longitude"

                // Handle your button click here
                // You can use the name, date, and location as needed.
                // For example, you can display the values in a toast message:
                val message = "Name: $name\nDate: $date\nLongitude: $longitude\nLatitude: $latitude ${
                    currentLocation?.latitude ?: 0.0
                }, Long: ${currentLocation?.longitude ?: 0.0}"
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()


                // Call the saveEntry function with the provided data
                saveEntry(name, longitude, latitude, date)
            }

            // Set click listeners for the "Add" and "Refresh" buttons
            val btnAdd = createchecklistsfrag?.findViewById<Button>(R.id.btnAdd)
            val btnRefresh = createchecklistsfrag?.findViewById<Button>(R.id.btnRefresh)
            val btnView = createchecklistsfrag?.findViewById<Button>(R.id.btnView)

            btnAdd?.setOnClickListener {
                // Handle the "Add" button click here
                btnAddClick()
            }

            btnRefresh?.setOnClickListener {
                // Handle the "Refresh" button click here
                btnRefreshClick()
            }

            btnView?.setOnClickListener {
                val intent = Intent(requireActivity(), BirdListAdapter::class.java)
                startActivity(intent)
            }
        }

        return createchecklistsfrag
    }

    private fun getCurrentLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result
                    if (location == null)
                    {
                        Toast.makeText(requireContext(), "Null Recieved", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        Toast.makeText(requireContext(), "Successfully Retrieved", Toast.LENGTH_SHORT).show()
                        textView_longitude.text=""+location.longitude
                        textView_latitude.text=""+location.latitude
                    }
                }
            }
                else
                {
                    Toast.makeText(requireContext(), "Turn on Location", Toast.LENGTH_SHORT).show()
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                }

            } else {
                requestPermission()
            }
        }


    private fun isLocationEnabled(): Boolean{
        val locationManager:LocationManager=requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_ACCESS_LOCATION)
    }




    private fun checkPermissions():Boolean{
        if(ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_COARSE_LOCATION)
        ==PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(),
            android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
        {
            return true
        }

        return false
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode== PERMISSION_REQUEST_ACCESS_LOCATION)
        {
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(requireContext(), "Granted", Toast.LENGTH_SHORT).show()
                getCurrentLocation()
            }
            else
            {
                Toast.makeText(requireContext(), "Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun saveEntry(name: String, longitude: Double, latitude: Double, date: String) {
        // Implement your logic to save the entry here
        // This is where you can save the data to a database or perform other actions.
        // For example, you can display a toast message:
        val message = "Saved!\nName: $name\nDate: $date\nLongitude: $longitude\nLatitude: $latitude"
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

        // Database Save
        val fAuth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()
        val user = fAuth.currentUser
        val observations = firestore.collection("users").document(user!!.uid).collection("Observations").document("data")

        val observationData: MutableMap<String, Any> = HashMap()
        observationData["name"] = name
        observationData["date"] = date
        observationData["longitude"] = longitude
        observationData["latitude"] = latitude
        observations.set(observationData, SetOptions.merge())
    }

    private fun btnAddClick() {
        val name = binding?.editTextText2?.text.toString()
        val date = binding?.editTextText4?.text.toString()
        val longitude = currentLocation?.longitude ?:0.0
        val latitude = currentLocation?.latitude ?:0.0

        // Check if any of the fields are empty
        if (name.isBlank() || date.isBlank()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // You can perform an action when the "Add" button is clicked.
        // For example, you can add the checklist/observation to a list or database.

        // Here's a sample action:
        val newEntry = "Name: $name, Date: $date, Longitude: $longitude, Latitude: $latitude"
        // Store or display the newEntry as needed

        // Clear the input fields
        binding?.editTextText2?.text?.clear()
        binding?.editTextText4?.text?.clear()


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
        private const val PERMISSION_REQUEST_ACCESS_LOCATION=100
    }


}
