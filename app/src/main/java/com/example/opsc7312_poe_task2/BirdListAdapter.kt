package com.example.opsc7312_poe_task2

import BirdItem
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

// Adapter for displaying a list of bird items in a RecyclerView
class BirdListAdapter(options: FirestoreRecyclerOptions<BirdItem>, private val context: Context) :
    FirestoreRecyclerAdapter<BirdItem, BirdListAdapter.BirdViewHolder>(options) {

    // ViewHolder for an individual bird item
    inner class BirdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtDate: TextView = itemView.findViewById(R.id.txtDate)
        val txtLocation: TextView = itemView.findViewById(R.id.txtLocation)
    }

    // Inflates the bird item view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BirdViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bird_list_item, parent, false)
        return BirdViewHolder(view)
    }

    // Binds data to the bird item view
    override fun onBindViewHolder(holder: BirdViewHolder, position: Int, model: BirdItem) {
        holder.txtName.text = model.name
        holder.txtDate.text = model.date
        holder.txtLocation.text = "Latitude: ${model.latitude}, Longitude: ${model.longitude}"
    }
}
