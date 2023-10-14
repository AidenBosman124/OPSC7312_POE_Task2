package com.example.opsc7312_poe_task2


import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import android.Manifest
import android.graphics.Color
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL
import java.util.concurrent.Executors


class Nearbyfrag : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var mapView: MapView
    lateinit var variablesList:List<Place>
    var userLatitude: Double= 0.0
    var userLongitude: Double= 0.0
    var distance: Int = 25
    private lateinit var Map : GoogleMap
    private var currentPolyline: Polyline? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val fragment_nearbyfrag = inflater.inflate(R.layout.fragment_nearbyfrag, container, false)
        //Adjusting/Declaring buttons and values
        val btnnearbyenter: Button = fragment_nearbyfrag.findViewById(R.id.btnnearbyenter)
        val txtnearbylocation: TextView = fragment_nearbyfrag.findViewById(R.id.txtnearbylocation)
        val seekbar: SeekBar = fragment_nearbyfrag.findViewById(R.id.distanceSB)
        val txthotspotnearest: TextView = fragment_nearbyfrag.findViewById(R.id.txtRegEmail)
        val txthotspotnearestdist: TextView = fragment_nearbyfrag.findViewById(R.id.txtRegPassword)
        mapView = fragment_nearbyfrag.findViewById(R.id.map)

        val mapViewBundle = savedInstanceState?.getBundle(MAPVIEW_BUNDLE_KEY)
        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(this)

        seekbar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
            }override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
                distance = seek.progress
                Toast.makeText(context,
                    "" + seek.progress + "km",
                    Toast.LENGTH_SHORT).show()

                Map.clear()

                // Call the InfoGetter function again to fetch the latest data
                InfoGetter(Map, LatLng(userLatitude, userLongitude))
            }
        })



        return fragment_nearbyfrag

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY) ?: Bundle().also {
            outState.putBundle(MAPVIEW_BUNDLE_KEY, it)
        }
        mapView.onSaveInstanceState(mapViewBundle)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onMapReady(map: GoogleMap) {
        map.setOnMarkerClickListener(this)
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat.requestPermissions
            // here to request the missing permissions, and then overriding
            //   fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
            //                                  grantResults: IntArray)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat.requestPermissions for more details.
            return
        }
        Map = map
        map.isMyLocationEnabled = true
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    userLatitude = location.latitude
                    userLongitude = location.longitude
                    val userLatLong = LatLng(userLatitude, userLongitude)
                    InfoGetter(map , userLatLong)
                }
            }
    }
    override fun onMarkerClick(marker: Marker): Boolean {
        currentPolyline?.remove()
        val URL = getDirectionURL(LatLng(userLatitude, userLongitude),LatLng( marker.position.latitude,marker.position.longitude))
        Log.d("GoogleMap", "URL : $URL")
        GetDirection(URL).execute()

        return false
    }

    private fun InfoGetter(map: GoogleMap , userLatLong: LatLng) {
        val executor = Executors.newSingleThreadExecutor()

        executor.execute {
            val url = URL("https://api.ebird.org/v2/ref/hotspot/geo?lat=$userLatitude&lng=$userLongitude&fmt=json&dist=$distance")
            val json = url.readText()
            variablesList = Gson().fromJson(json, Array<Place>::class.java).toList()
            Handler(Looper.getMainLooper()).post {

                // After getting the data, you can add markers on the map and move the camera.
                for (i in variablesList.indices) {
                    val latlng = LatLng(variablesList[i].lat, variablesList[i].lng)
                    map.addMarker(MarkerOptions().position(latlng).title(variablesList[i].locName).snippet("Number of Species " + variablesList[i].numSpeciesAllTime))
                    map.animateCamera(CameraUpdateFactory.zoomTo(18.0f))
                    map.moveCamera(CameraUpdateFactory.newLatLng(userLatLong))
                }
            }
        }
    }


    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    companion object {
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
    }

    fun getDirectionURL(origin:LatLng,dest:LatLng) : String{
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}&destination=${dest.latitude},${dest.longitude}&sensor=false&mode=driving&key=AIzaSyD2oJm5S0-bA7l3-0u_Xr-bGYuTqhubKuY"
    }

    private inner class GetDirection(val url : String) : AsyncTask<Void, Void, List<List<LatLng>>>(){
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body!!.string()
            Log.d("GoogleMap" , " data : $data")
            val result =  ArrayList<List<LatLng>>()
            try{
                val respObj = Gson().fromJson(data,GoogleMapDTO::class.java)

                val path =  ArrayList<LatLng>()

                for (i in 0..(respObj.routes[0].legs[0].steps.size-1)){
                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }
                result.add(path)
            }catch (e:Exception){
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>) {
            val lineoption = PolylineOptions()
            for (i in result.indices){
                lineoption.addAll(result[i])
                lineoption.width(10f)
                lineoption.color(Color.BLUE)
                lineoption.geodesic(true)
            }
            currentPolyline = Map.addPolyline(lineoption)
        }
    }
    fun decodePolyline(encoded: String): List<LatLng> {

        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val latLng = LatLng((lat.toDouble() / 1E5),(lng.toDouble() / 1E5))
            poly.add(latLng)
        }

        return poly
    }
}
