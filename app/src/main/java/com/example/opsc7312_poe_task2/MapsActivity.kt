package com.example.opsc7312_poe_task2

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.opsc7312_poe_task2.databinding.ActivityMapsBinding
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.*



class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var mMap: GoogleMap
    //private lateinit var binding: ActivityMapsBinding
    private val LOCATION_PERMISSION_REQUEST_CODE = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)


       // mapView = findViewById(R.id.mapView)
        //mapView.onCreate(savedInstanceState)

       // mapView.getMapAsync { map ->
        ///    mMap = map

          //  binding = ActivityMapsBinding.inflate(layoutInflater)
          //  setContentView(binding.root)

       // }
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }



    override fun onResume() {
        super.onResume()
       // mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        //mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        //mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
       // mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
       // mapView.onSaveInstanceState(outState)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true

        // Enable the My Location layer on the map
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            enableMyLocation()
        } else {
            // Request the missing location permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }
        private fun enableMyLocation() {
            mMap.isMyLocationEnabled = true

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.isMyLocationEnabled = true

                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        val userLatLng = LatLng(location.latitude, location.longitude)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 12f))

                        // Fetch eBird hotspots based on user's location
                        fetchBirdingHotspots(location.latitude, location.longitude)

                        // Add a marker for the user's location
                        mMap.addMarker(MarkerOptions().position(userLatLng).title("Your Location"))
                    }
                }
            } else {
                // Permission is not granted, request it
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }

    fun fetchBirdingHotspots(latitude: Double, longitude: Double) {
        val eBirdAPIUrl = "https://api.ebird.org/v2/ref/geo/loc/recent"

        val requestQueue = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(
            Request.Method.GET,
            "$eBirdAPIUrl?lat=$latitude&lng=$longitude",
            null,
            { response ->
                // Handle the API response
            },
            { error ->
                // Handle errors
            }
        )

        requestQueue.add(request)
    }
}