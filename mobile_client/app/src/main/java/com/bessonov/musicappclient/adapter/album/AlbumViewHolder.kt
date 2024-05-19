package com.bessonov.musicappclient.adapter.album

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R

class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val albumName: TextView = itemView.findViewById(R.id.itemAlbum_AlbumName)
    val albumImage: ImageView = itemView.findViewById(R.id.itemAlbum_AlbumImage)
    val artistName: TextView = itemView.findViewById(R.id.itemAlbum_ArtistName)
}