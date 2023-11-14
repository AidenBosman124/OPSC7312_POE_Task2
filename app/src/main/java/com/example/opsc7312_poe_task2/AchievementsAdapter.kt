package com.example.opsc7312_poe_task2

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AchievementsAdapter(private var achievements: List<HelperClass.Achievement>) :
    RecyclerView.Adapter<AchievementsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_achievement, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val achievement = achievements[position]
        holder.bind(achievement, achievement.id.toString(), achievement.isUnlocked)
    }

    override fun getItemCount(): Int = achievements.size

    fun updateData(newAchievements: List<HelperClass.Achievement>) {
        achievements = newAchievements.toList()
        notifyDataSetChanged()
        Log.d("AchievementsAdapter", "Data updated. New size: ${achievements.size}")
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewAchievement: ImageView = itemView.findViewById(R.id.imageViewAchievement)
        private val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        private val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        private val textViewStatus: TextView = itemView.findViewById(R.id.textViewStatus)

        fun bind(achievement: HelperClass.Achievement, achID: String, achStatus: Boolean) {

            val levelPrefix = getLevelPrefix(achID)
            val isUnlocked = achStatus

            val imageResource = when (levelPrefix) {
                "gold" -> R.drawable.goldtrophyimg
                "silver" -> R.drawable.silvertrophyimg
                "bronze" -> R.drawable.bronzetrophyimg
                else -> R.drawable.goldtrophyimg
            }

            if (isUnlocked) {
                imageViewAchievement.setImageResource(imageResource)
                imageViewAchievement.colorFilter = null
            } else {
                val colorMatrix = ColorMatrix()
                colorMatrix.setSaturation(0f)
                val colorFilter = ColorMatrixColorFilter(colorMatrix)
                imageViewAchievement.setImageResource(imageResource)
                imageViewAchievement.colorFilter = colorFilter
            }

            textViewName.text = achievement.name
            textViewDescription.text = achievement.description
            textViewStatus.text = if (achievement.isUnlocked) "Unlocked" else "Locked"
        }

        private fun getLevelPrefix(achievementName: String): String {
            val prefix = achievementName.substringBefore('-').toLowerCase()
            Log.d("AchievementsAdapter", "Achievement ID: $achievementName, Level Prefix: $prefix")
            return prefix
        }
    }
}
