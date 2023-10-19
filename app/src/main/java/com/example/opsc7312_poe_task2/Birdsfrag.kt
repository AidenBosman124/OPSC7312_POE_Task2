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

        // Initialize your list of bird items here
        val birdItems = listOf(
            BirdItem(R.drawable.bird1, "Bird 1", "2023-10-20", "Location 1"),
            BirdItem(R.drawable.bird2, "Bird 2", "2023-10-21", "Location 2"),
            // Add more bird items as needed
        )

        // Set up a RecyclerView adapter
        val adapter = BirdListAdapter(birdItems)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        return birdsfrag
    }
}
