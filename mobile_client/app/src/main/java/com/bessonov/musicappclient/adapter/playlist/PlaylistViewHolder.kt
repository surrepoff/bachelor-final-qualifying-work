package com.bessonov.musicappclient.adapter.playlist

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R

class PlaylistViewHolder(
    itemView: View,
    orientation: Int
) : RecyclerView.ViewHolder(itemView) {
    val playlistName: TextView
    val playlistImage: ImageView
    val ownerName: TextView
    val addButton: ImageButton?
    val likeButton: ImageButton?
    val dislikeButton: ImageButton?

    init {
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            playlistName = itemView.findViewById(R.id.itemPlaylistHorizontal_playlistName)
            playlistImage = itemView.findViewById(R.id.itemPlaylistHorizontal_playlistImage)
            ownerName = itemView.findViewById(R.id.itemPlaylistHorizontal_ownerName)
            addButton = null
            likeButton = null
            dislikeButton = null
        } else {
            playlistName = itemView.findViewById(R.id.itemPlaylistVertical_playlistName)
            playlistImage = itemView.findViewById(R.id.itemPlaylistVertical_playlistImage)
            ownerName = itemView.findViewById(R.id.itemPlaylistVertical_ownerName)
            addButton = itemView.findViewById(R.id.itemPlaylistVertical_addButton)
            likeButton = itemView.findViewById(R.id.itemPlaylistVertical_likeButton)
            dislikeButton = itemView.findViewById(R.id.itemPlaylistVertical_dislikeButton)
        }
    }
}