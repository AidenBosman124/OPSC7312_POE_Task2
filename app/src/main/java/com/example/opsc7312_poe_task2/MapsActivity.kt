package com.example.opsc7312_poe_task2

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AlertDialog
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
import com.example.opsc7312_poe_task2.databinding.ActivityMapsBinding
import com.example.opsc7312_poe_task2.CustomInfoWindowAdapter
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.maps.DirectionsApiRequest
import com.google.maps.GeoApiContext
import com.google.maps.PendingResult
import com.google.maps.internal.PolylineEncoding
import com.google.maps.model.DirectionsResult
import com.google.maps.model.Unit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener,
    GoogleMap.OnPolylineClickListener {


    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private var mCurrentLocation: Location = Location("dummy_provider")
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var cameraMovedToUserLocation = false
    private var lastHotspotLoadLocation: Location? = null
    private val hotspotReloadDistanceThreshold = 10000
    private var currentLocationCallback: LocationCallback? = null
    private var mGeoApiContext: GeoApiContext? = null
    private var mPolyLinesData: ArrayList<PolylineData> = ArrayList()
    private var mSelectedMarker: Marker? = null
    private var mTripMarkers: ArrayList<Marker> = ArrayList()
    private val API_KEY ="AIzaSyBtY6H9DrcG7C77YjH5oLOk4vF-M4wrSdI"

    private val eBird_BASE_URL = "https://api.ebird.org/v2/"
    private var selectedUnits: Boolean = false
    private var selectedDistance: Int = 20


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true

        val customInfoWindowAdapter = CustomInfoWindowAdapter(this)
        mMap.setInfoWindowAdapter(customInfoWindowAdapter)

        if (mGeoApiContext == null){
            mGeoApiContext = GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build()
        }
        mMap.setOnInfoWindowClickListener(this)
        mMap.setOnPolylineClickListener(this)
        getDeviceLocation()


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

    private fun getHotspots(){
        Log.d(ContentValues.TAG, "getHotspots: Called GETHOTSPOTS")
        Log.d(ContentValues.TAG, "mCurrentLocation latidude ${mCurrentLocation.latitude}")
        Log.d(ContentValues.TAG, "mCurrentLocation longidude ${mCurrentLocation.longitude}")

        //convert miles to kilometeres if the user has selected imperial
        if(selectedUnits){
            selectedDistance = (selectedDistance*1.609).roundToInt()
        }
        Log.d(ContentValues.TAG, "SELECTED DISTANCE: $selectedDistance")

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(eBird_BASE_URL)
            .build()
            .create(RetrofitInterface::class.java)

        val retrofitData = retrofitBuilder.getHotspots(
            latitude = mCurrentLocation.latitude,
            longitude = mCurrentLocation.longitude,
            distance = selectedDistance
        )

        retrofitData.enqueue(object : Callback<List<Hotspot>?> {
            override fun onResponse(
                call: Call<List<Hotspot>?>,
                response: Response<List<Hotspot>?>
            ) {
                val responseBody = response.body()!!

                for (hotspot in responseBody) {
                    val hotspotLocation = LatLng(hotspot.lat, hotspot.lng)
                    val hotspotMarkerOptions = MarkerOptions()
                        .position(hotspotLocation)
                        .title(hotspot.locName)
                        .snippet("Last Observed: ${hotspot.latestObsDt}\nSpecies Spotted: ${hotspot.numSpeciesAllTime}")
                    mMap.addMarker(hotspotMarkerOptions)
                }
            }

            override fun onFailure(call: Call<List<Hotspot>?>, t: Throwable) {
                Log.d(ContentValues.TAG, "onFailure: $t")
            }
        })
    }

    private fun getDeviceLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        mMap.isMyLocationEnabled = true
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 3000).apply {
            setMinUpdateDistanceMeters(20F)
            setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
            setWaitForAccurateLocation(true)
        }.build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                p0.lastLocation?.let { location ->
                    mCurrentLocation = location
                    Log.d(ContentValues.TAG, "New Location - Latitude: ${location.latitude}, Longitude: ${location.longitude}")
                    if(!cameraMovedToUserLocation){
                        val currentLatLng = LatLng(location.latitude, location.longitude)
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                        cameraMovedToUserLocation = true
                    }
                    if (lastHotspotLoadLocation == null ||
                        location.distanceTo(lastHotspotLoadLocation!!) >= hotspotReloadDistanceThreshold) {
                        // Load hotspot markers only if the user has traveled a significant distance
                        mMap.clear()
                        getHotspots()
                        lastHotspotLoadLocation = location
                    }
                }
            }
        }
        currentLocationCallback = locationCallback
        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }


    private fun fetchBirdingHotspots(latitude: Double, longitude: Double) {
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

    private fun addPolylinesToMap(result: DirectionsResult) {
        Handler(Looper.getMainLooper()).post {
            Log.d(ContentValues.TAG, "run: result routes: " + result.routes.size)
            if (mPolyLinesData.size > 0) {
                for (polylineData in mPolyLinesData) {
                    polylineData.getPolyline()!!.remove()
                }
                mPolyLinesData.clear()
                mPolyLinesData = ArrayList()
            }
            var duration = 9999999
            for (route in result.routes) {
                Log.d(ContentValues.TAG, "run: leg: " + route.legs[0].toString())
                val decodedPath = PolylineEncoding.decode(route.overviewPolyline.encodedPath)
                val newDecodedPath: MutableList<LatLng> = ArrayList()

                // This loops through all the LatLng coordinates of ONE polyline.
                for (latLng in decodedPath) {
                    newDecodedPath.add(
                        LatLng(
                            latLng.lat,
                            latLng.lng
                        )
                    )
                }
                val polyline: Polyline =
                    mMap.addPolyline(PolylineOptions().addAll(newDecodedPath).width(20F))
                polyline.color = ContextCompat.getColor(this, R.color.light_blue)
                polyline.isClickable = true
                mPolyLinesData.add(PolylineData(polyline, route.legs[0]))

                val tempDuration = route.legs[0].duration.inSeconds
                if (tempDuration < duration) {
                    duration = tempDuration.toInt()
                    onPolylineClick(polyline)
                    zoomRoute(polyline.points)
                }
                mSelectedMarker?.isVisible = false
            }
        }
    }

    private fun zoomRoute(lstLatLngRoute: List<LatLng?>?) {
        if (lstLatLngRoute == null || lstLatLngRoute.isEmpty()) return
        val boundsBuilder = LatLngBounds.Builder()
        for (latLngPoint in lstLatLngRoute) boundsBuilder.include(latLngPoint!!)
        val routePadding = 120
        val latLngBounds = boundsBuilder.build()
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(latLngBounds, routePadding),
            600,
            null
        )
    }

    private fun calculateDirections(marker: Marker){
        Log.d(ContentValues.TAG, "calculateDirections: calculating directions.")

        val destination = com.google.maps.model.LatLng(
            marker.position.latitude,
            marker.position.longitude
        )
        val directions = DirectionsApiRequest(mGeoApiContext)
        directions.alternatives(true)
        if(!selectedUnits){
            directions.units(Unit.METRIC)
        }else{
            directions.units(Unit.IMPERIAL)
        }

        directions.origin(
            com.google.maps.model.LatLng(
                mCurrentLocation.latitude,
                mCurrentLocation.longitude
            )
        )
        Log.d(ContentValues.TAG, "calculateDirections: destination: $destination")
        directions.destination(destination)
            .setCallback(object : PendingResult.Callback<DirectionsResult> {
                override fun onResult(result: DirectionsResult) {
                    Log.d(ContentValues.TAG, "calculateDirections: routes: " + result.routes[0].toString())
                    Log.d(
                        ContentValues.TAG, "calculateDirections: duration: " + result.routes[0].legs[0].duration
                    )
                    Log.d(
                        ContentValues.TAG, "calculateDirections: distance: " + result.routes[0].legs[0].distance
                    )
                    Log.d(
                        ContentValues.TAG, "calculateDirections: geocodedWayPoints: " + result.geocodedWaypoints[0].toString()
                    )
                    addPolylinesToMap(result)
                }

                override fun onFailure(e: Throwable) {
                    Log.e(ContentValues.TAG, "calculateDirections: Failed to get directions: " + e.message)
                }
            })
    }

    private fun removeTripMarkers(){
        for(marker in mTripMarkers){
            marker.remove()
        }
    }

    private fun resetSelectedMarker(){
        if(mSelectedMarker != null){
            mSelectedMarker!!.isVisible = true
            mSelectedMarker = null
            removeTripMarkers()
        }
    }

    override fun onInfoWindowClick(marker: Marker) {
        Log.d(ContentValues.TAG, "onInfoWindowClick: INFO WINDOW CLICKED")
        if (marker.title != null && marker.title!!.contains("Trip #")) {
            return
        }
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Directions to ${marker.title}")
            .setMessage("Do you want to calculate directions to this hotspot?")
            .setPositiveButton("Yes") { _, _ ->
                calculateDirections(marker)
                resetSelectedMarker()
                mSelectedMarker = marker
            }
            .setNegativeButton("No") { _, _ ->
            }
        builder.show()
    }


    override fun onPolylineClick(polyline: Polyline) {
        var index = 0
        for (polylineData in mPolyLinesData) {
            index++
            Log.d(ContentValues.TAG, "onPolylineClick: toString: $polylineData")
            if (polyline.id == polylineData.getPolyline()!!.id) {
                polylineData.getPolyline()!!.color =
                    ContextCompat.getColor(this, R.color.black)
                polylineData.getPolyline()!!.zIndex = 1f

                val endLocation = LatLng(
                    polylineData.getLeg()!!.endLocation.lat,
                    polylineData.getLeg()!!.endLocation.lng
                )

                val markerOptions = MarkerOptions()
                    .position(endLocation)
                    .title("Trip #$index")
                    .snippet("Duration: ${polylineData.getLeg()!!.duration}\nDistance: ${polylineData.getLeg()!!.distance}")
                val marker = mMap.addMarker(markerOptions)
                marker?.showInfoWindow()
                if (marker != null) {
                    mTripMarkers.add(marker)
                }
            } else {
                polylineData.getPolyline()!!.color =
                    ContextCompat.getColor(this, R.color.light_blue)
                polylineData.getPolyline()!!.zIndex = 0f
            }
        }
    }
}