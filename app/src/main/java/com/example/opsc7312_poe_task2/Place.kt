package com.example.opsc7312_poe_task2

import com.google.android.gms.maps.model.LatLng

// Data class to represent a place
data class Place(
    val locId: String,              // Unique location ID
    val locName: String,            // Name of the location
    val countryCode: String,        // Country code
    val subnational1Code: String,   // Subnational (e.g., state) code
    val lat: Double,                // Latitude coordinate
    val lng: Double,                // Longitude coordinate
    val latestObsDt: String,        // Latest observation date
    val numSpeciesAllTime: String   // Number of species observed at this location over time
)
