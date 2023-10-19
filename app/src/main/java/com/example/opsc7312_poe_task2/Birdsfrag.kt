package com.example.opsc7312_poe_task2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Birdsfrag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val birdsfrag = inflater.inflate(R.layout.fragment_birdsfrag, container, false)
        val recyclerView = birdsfrag.findViewById<RecyclerView>(R.id.recyclerView)

        // Retrieve data from arguments with default values
        val birdName = arguments?.getString("birdName", "Bird 1") ?: "Bird 1"
        val sightingDate = arguments?.getString("sightingDate", "2023-10-20") ?: "2023-10-20"
        val sightingLocation = arguments?.getString("sightingLocation", "Location 1") ?: "Location 1"

        // Initialize your list of bird items using the retrieved data
        val birdItems = listOf(
            BirdItem(R.mipmap.ic_launcher_round, birdName, sightingDate, sightingLocation)
            // Add more bird items as needed
        )

        // Set up a RecyclerView adapter
        val adapter = BirdListAdapter(birdItems)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        return birdsfrag
    }
}
