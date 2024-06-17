package com.bessonov.musicappclient.adapter.trackAdd

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R

class TrackAddViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val trackImage: ImageView = itemView.findViewById(R.id.itemTrackAdd_trackImage)
    val trackName: TextView = itemView.findViewById(R.id.itemTrackAdd_trackName)
    val artistName: TextView = itemView.findViewById(R.id.itemTrackAdd_artistName)
    val trackDuration: TextView = itemView.findViewById(R.id.itemTrackAdd_trackDuration)
    val addButton: ImageButton = itemView.findViewById(R.id.itemTrackAdd_addButton)
}