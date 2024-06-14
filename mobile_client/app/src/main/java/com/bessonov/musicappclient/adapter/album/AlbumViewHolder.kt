package com.bessonov.musicappclient.adapter.album

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R

class AlbumViewHolder(
    itemView: View,
    orientation: Int
) : RecyclerView.ViewHolder(itemView) {
    val albumName: TextView
    val albumImage: ImageView
    val artistName: TextView
    val addButton: ImageButton?
    val likeButton: ImageButton?
    val dislikeButton: ImageButton?

    init {
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            albumName = itemView.findViewById(R.id.itemAlbumHorizontal_albumName)
            albumImage = itemView.findViewById(R.id.itemAlbumHorizontal_albumImage)
            artistName = itemView.findViewById(R.id.itemAlbumHorizontal_artistName)
            addButton = null
            likeButton = null
            dislikeButton = null
        }
        else {
            albumName =  itemView.findViewById(R.id.itemAlbumVertical_albumName)
            albumImage = itemView.findViewById(R.id.itemAlbumVertical_albumImage)
            artistName = itemView.findViewById(R.id.itemAlbumVertical_artistName)
            addButton = itemView.findViewById(R.id.itemAlbumVertical_addButton)
            likeButton = itemView.findViewById(R.id.itemAlbumVertical_likeButton)
            dislikeButton = itemView.findViewById(R.id.itemAlbumVertical_dislikeButton)
        }
    }
}