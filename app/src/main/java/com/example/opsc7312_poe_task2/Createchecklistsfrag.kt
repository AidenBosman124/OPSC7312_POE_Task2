package com.example.opsc7312_poe_task2

import android.content.ContentValues
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.opsc7312_poe_task2.BirdItem
import com.example.opsc7312_poe_task2.HelperClass
import com.example.opsc7312_poe_task2.R
import com.example.opsc7312_poe_task2.birdAdapter
import java.text.SimpleDateFormat
import java.util.*

class Createchecklistsfrag : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: birdAdapter
    lateinit var helperClass: HelperClass

    private lateinit var editText: EditText
    private lateinit var mCurrentLocation: Location

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_createchecklistsfrag, container, false)

        editText = view.findViewById(R.id.editTextBird)

        val birdList = getBirdData()

        val addButton = view.findViewById<Button>(R.id.btnAdd)
        val refButton = view.findViewById<Button>(R.id.btnRefresh)

        addButton?.setOnClickListener {
            btnAddClick()
        }

        refButton?.setOnClickListener {
            btnRefreshClick()
        }

        helperClass = HelperClass()
        recyclerView = view.findViewById(R.id.recyclerView)

        adapter = birdAdapter(birdList)
        recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        return view
    }

    private fun getBirdData(): List<BirdItem> {
        val birdList = mutableListOf<BirdItem>()
        return birdList
    }
    private fun getLocationName(location: Location): String {
        val geocoder = Geocoder(requireContext())
        var returnName = ""
        try {
            Log.d(ContentValues.TAG, "New Location - Latitude: ${location.latitude}, Longitude: ${location.longitude}")
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            Log.d(ContentValues.TAG, "ADDRESSES: ${addresses?.get(0)}")
            if (addresses != null) {
                if (addresses.isNotEmpty()) {
                    val address = addresses[0]
                    val cityName = address.locality
                    val addressString = address.getAddressLine(0)
                    Log.d(ContentValues.TAG, "ADDRESS: ${address.countryName} AND $addressString")
                    returnName = "$cityName, $addressString"
                    Log.d(ContentValues.TAG, "getLocationName THIS IS THE NAME: $returnName")
                } else {
                    Log.d(ContentValues.TAG, "getLocationNameFromCoordinates: COULD NOT GET LOCATION NAME")
                    returnName = ""
                }
            }
        } catch (e: Exception) {
            Log.d(ContentValues.TAG, "getLocationNameFromCoordinates: EXCEPTION $e")
        }
        return returnName
    }

    private fun btnAddClick() {
        if (editText.text.isNotEmpty()) {
            val name = editText.text.toString()

            val loc = getLocationName(mCurrentLocation)
            val date = getCurrentDateTime()

            saveEntry(name, loc, date)
        } else {
            Toast.makeText(requireContext(), "Invalid Input", Toast.LENGTH_LONG).show()
        }
    }

    private fun btnRefreshClick() {
        val birdList = updateList(helperClass.BirdMap)
        adapter = birdAdapter(birdList)
        recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        Toast.makeText(requireContext(), "Refreshed!", Toast.LENGTH_SHORT).show()
    }

    private fun updateList(birdMap: HashMap<String, HelperClass.Bird>): List<BirdItem> {
        val birdList = mutableListOf<BirdItem>()

        if (birdMap.isNotEmpty()) {
            for ((birdName, bird) in birdMap) {
                birdList.add(BirdItem(name = birdName, dateTime = bird.dateTime, location = bird.location))
            }
        } else {
            Toast.makeText(requireContext(), "No Saved Observations", Toast.LENGTH_LONG).show()
        }
        return birdList
    }


    private fun saveEntry(name: String, location: String, date: String) {
        helperClass.addToList(name, name, location, date)
        Toast.makeText(requireContext(), "Saved!", Toast.LENGTH_SHORT).show()
    }

    private fun getCurrentDateTime(): String {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(cal.time)
    }
}
