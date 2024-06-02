package com.bessonov.musicappclient.adapter.artist

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R

class ArtistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val artistName: TextView = itemView.findViewById(R.id.itemArtistVertical_artistName)
    val artistImage: ImageView = itemView.findViewById(R.id.itemArtistVertical_artistImage)
}