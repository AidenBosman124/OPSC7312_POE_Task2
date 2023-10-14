package com.example.opsc7312_poe_task2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class Checklistsfrag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout XML resource file 'fragment_checklistsfrag' to create the user interface of this fragment.
        val checklistsfrag = inflater.inflate(R.layout.fragment_checklistsfrag, container, false)

        // Return the View representing the user interface of this fragment to be displayed on the screen.
        return checklistsfrag
    }
}
