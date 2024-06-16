package com.bessonov.musicappclient.adapter.playlistCreate

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R

class PlaylistCreateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val createButton: Button = itemView.findViewById(R.id.itemPlaylistCreate_createButton)
}