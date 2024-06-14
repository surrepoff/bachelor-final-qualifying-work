package com.bessonov.musicappclient.adapter.artist

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R

class ArtistViewHolder(
    itemView: View,
    orientation: Int
) : RecyclerView.ViewHolder(itemView) {
    val artistName: TextView
    val artistImage: ImageView
    val addButton: ImageButton?
    val likeButton: ImageButton?
    val dislikeButton: ImageButton?

    init {
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            artistName = itemView.findViewById(R.id.itemArtistHorizontal_artistName)
            artistImage = itemView.findViewById(R.id.itemArtistHorizontal_artistImage)
            addButton = null
            likeButton = null
            dislikeButton = null
        }
        else {
            artistName =  itemView.findViewById(R.id.itemArtistVertical_artistName)
            artistImage = itemView.findViewById(R.id.itemArtistVertical_artistImage)
            addButton = itemView.findViewById(R.id.itemArtistVertical_addButton)
            likeButton = itemView.findViewById(R.id.itemArtistVertical_likeButton)
            dislikeButton = itemView.findViewById(R.id.itemArtistVertical_dislikeButton)
        }
    }
}