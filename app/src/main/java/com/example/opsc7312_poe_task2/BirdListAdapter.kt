package com.example.opsc7312_poe_task2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BirdListAdapter(private val birdItems: List<BirdItem>, private val context: Context) :
    RecyclerView.Adapter<BirdListAdapter.BirdViewHolder>() {

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

    override fun onBindViewHolder(holder: BirdViewHolder, position: Int) {
        val currentItem = birdItems[position]
        holder.birdName.text = currentItem.name
        holder.birdDate.text = currentItem.date
        holder.birdLocation.text = currentItem.location

        // Load image using Glide library
        Glide.with(context)
            .load(currentItem.imageResource)
            .into(holder.birdImage)
    }

    override fun getItemCount(): Int {
        return birdItems.size
    }
}
