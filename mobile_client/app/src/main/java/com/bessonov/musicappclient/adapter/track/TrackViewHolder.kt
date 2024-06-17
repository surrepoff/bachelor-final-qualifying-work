package com.bessonov.musicappclient.adapter.track

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R

class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val trackImage: ImageView = itemView.findViewById(R.id.itemTrack_trackImage)
    val trackName: TextView = itemView.findViewById(R.id.itemTrack_trackName)
    val artistName: TextView = itemView.findViewById(R.id.itemTrack_artistName)
    val trackDuration: TextView = itemView.findViewById(R.id.itemTrack_trackDuration)
    val addButton: ImageButton = itemView.findViewById(R.id.itemTrack_addButton)
    val likeButton: ImageButton = itemView.findViewById(R.id.itemTrack_likeButton)
    val dislikeButton: ImageButton = itemView.findViewById(R.id.itemTrack_dislikeButton)
}