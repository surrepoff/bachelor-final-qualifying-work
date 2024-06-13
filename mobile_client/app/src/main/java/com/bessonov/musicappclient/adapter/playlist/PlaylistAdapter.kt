package com.bessonov.musicappclient.adapter.playlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.dto.PlaylistInfoDTO

class PlaylistAdapter(
    private val context: Context,
    private val playlistInfoDTOList: List<PlaylistInfoDTO>
) : RecyclerView.Adapter<PlaylistViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_playlist_vertical, parent, false)
        return PlaylistViewHolder(view)
    }

    override fun getItemCount(): Int {
        return playlistInfoDTOList.size
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val playlistInfoDTO = playlistInfoDTOList[position]

        holder.playlistName.text = playlistInfoDTO.playlist.name

        var ownerName = ""
        ownerName += playlistInfoDTO.owner.joinToString(", ") {it.nickname}

        holder.ownerName.text = ownerName
    }
}