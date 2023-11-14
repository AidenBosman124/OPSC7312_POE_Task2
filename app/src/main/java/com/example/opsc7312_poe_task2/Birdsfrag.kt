package com.example.opsc7312_poe_task2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class Birdsfrag : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BirdListAdapter
    private val birdItems = mutableListOf<BirdItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val birdsfrag = inflater.inflate(R.layout.fragment_birdsfrag, container, false)
        recyclerView = birdsfrag.findViewById(R.id.recyclerView)

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize the adapter before using it
         recyclerView.adapter = adapter

        // Fetch data from Firebase
        fetchBirdDataFromFirebase()

        return birdsfrag
    }

    private fun fetchBirdDataFromFirebase() {
        // Assuming you have a Firebase Firestore collection named "birds"
        val fAuth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()
        val user = fAuth.currentUser
        val birdsCollection = firestore.collection("users").document(user!!.uid).collection("birds")

        // Query to retrieve data (modify as needed)
        val query: Query = birdsCollection.limit(10) // Limit to 10 items for example

        query.get().addOnSuccessListener { snapshot ->
            birdItems.clear() // Clear previous data
            for (document in snapshot.documents) {
                // Parse data from the document and create BirdItem objects
                val imageResource =
                    (document.get("imageResource") as? Long)?.toInt() ?: R.mipmap.ic_launcher_round
                val birdItem = BirdItem(
                    imageResource,
                    document.getString("birdName") ?: "Bird 1",
                    document.getString("sightingDate") ?: "2023-10-20",
                    document.getString("sightingLocation") ?: "Location 1"
                )
                birdItems.add(birdItem)
            }

            // Notify adapter that data has changed
            adapter.notifyDataSetChanged()
        }.addOnFailureListener { exception ->
            // Handle failure
            // You might want to log the exception or show an error message
        }
    }
}
