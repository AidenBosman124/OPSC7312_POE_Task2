package com.example.opsc7312_poe_task2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
class Settingsfrag : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragment_settingsfrag = inflater.inflate(R.layout.fragment_settingsfrag, container, false)
        //Adjusting/Declaring buttons and values
        val btnkilometres: Button = fragment_settingsfrag.findViewById(R.id.btnkilometres)
        val btnmiles: Button = fragment_settingsfrag.findViewById(R.id.btnmiles)
        val txtmaxdistance: TextView = fragment_settingsfrag.findViewById(R.id.txtmaxdistance)
        // Inflate the layout for this fragment
        return fragment_settingsfrag

        //Proper Methods
        //Set App to kilometres -> 1 Km = 0,621371 Miles
        btnkilometres.setOnClickListener(){
            val measurementKm = 1* 0.621371 //may work better as double value
        }
        //Set App to Miles -> 1 Mile = 1,60934 Km
        btnmiles.setOnClickListener(){
            val measurementM = 1* 1.60934 //may work better as double value
        }
        //Sets maximum distance for user
        txtmaxdistance.setOnClickListener(){
            val maxdistance = txtmaxdistance
        }
    }
}