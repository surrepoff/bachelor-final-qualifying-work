package com.bessonov.musicappclient.adapter.playlistCreate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R

class PlaylistCreateAdapter(
    private val context: Context,
    private val playlistCreateList: List<String>,
    private val onButtonClick: () -> Unit
) : RecyclerView.Adapter<PlaylistCreateViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaylistCreateViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_playlist_create, parent, false)
        return PlaylistCreateViewHolder(view)
    }

    override fun getItemCount(): Int {
        return playlistCreateList.size
    }

    override fun onBindViewHolder(holder: PlaylistCreateViewHolder, position: Int) {
        holder.createButton.setOnClickListener {
            onButtonClick.invoke()
        }
    }
}