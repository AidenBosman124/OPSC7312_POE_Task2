package com.example.opsc7312_poe_task2

import BirdItem
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class Birdsfrag : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BirdListAdapter
    private val firestore = FirebaseFirestore.getInstance()
    private val fAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val birdsfrag = inflater.inflate(R.layout.fragment_birdsfrag, container, false)
        recyclerView = birdsfrag.findViewById(R.id.recyclerView)

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize the adapter with FirestoreRecyclerOptions
        val query: Query = firestore.collection("users").document(fAuth.currentUser?.uid ?: "")
            .collection("Observations")
            .orderBy("date", Query.Direction.DESCENDING)  // You can order by date or any other field
        val options = FirestoreRecyclerOptions.Builder<BirdItem>()
            .setQuery(query, BirdItem::class.java)
            .build()

        // Use FirestoreRecyclerOptions instead of FirebaseRecyclerOptions
        adapter = BirdListAdapter(options, requireContext())
        recyclerView.adapter = adapter

        return birdsfrag
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}
