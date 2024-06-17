package com.bessonov.musicappclient.adapter.trackRemove

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R

class TrackRemoveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val trackImage: ImageView = itemView.findViewById(R.id.itemTrackRemove_trackImage)
    val trackName: TextView = itemView.findViewById(R.id.itemTrackRemove_trackName)
    val artistName: TextView = itemView.findViewById(R.id.itemTrackRemove_artistName)
    val trackDuration: TextView = itemView.findViewById(R.id.itemTrackRemove_trackDuration)
    val removeButton: ImageButton = itemView.findViewById(R.id.itemTrackRemove_removeButton)
}