package com.bessonov.musicappclient.adapter.track

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R

class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val trackImage: ImageView = itemView.findViewById(R.id.itemTrack_TrackImage)
    val trackName: TextView = itemView.findViewById(R.id.itemTrack_TrackName)
    val artistName: TextView = itemView.findViewById(R.id.itemTrack_ArtistName)
    val trackDuration: TextView = itemView.findViewById(R.id.itemTrack_TrackDuration)
}