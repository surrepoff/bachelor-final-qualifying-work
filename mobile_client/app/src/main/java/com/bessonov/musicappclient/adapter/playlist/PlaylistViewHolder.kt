package com.bessonov.musicappclient.adapter.playlist

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R

class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val playlistName: TextView = itemView.findViewById(R.id.itemPlaylistVertical_playlistName)
    val playlistImage: ImageView = itemView.findViewById(R.id.itemPlaylistVertical_playlistImage)
    val ownerName: TextView = itemView.findViewById(R.id.itemPlaylistVertical_ownerName)
}