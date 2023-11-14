package com.example.opsc7312_poe_task2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter

class BirdListAdapter(options: MutableList<BirdItem>, private val context: Context) :
    FirebaseRecyclerAdapter<BirdItem, BirdListAdapter.BirdViewHolder>(options) {

    class BirdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val birdImage: ImageView = itemView.findViewById(R.id.birdImage)
        val birdName: TextView = itemView.findViewById(R.id.birdName)
        val birdDate: TextView = itemView.findViewById(R.id.birdDate)
        val birdLocation: TextView = itemView.findViewById(R.id.birdLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BirdViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.bird_list_item, parent, false)
        return BirdViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BirdViewHolder, position: Int, model: BirdItem) {
        holder.birdName.text = model.name
        holder.birdDate.text = model.date
        holder.birdLocation.text = model.location

        // Load image using Glide library
        Glide.with(context)
            .load(model.imageResource)
            .into(holder.birdImage)
    }
}
