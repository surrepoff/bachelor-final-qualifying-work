package com.bessonov.musicappclient.adapter.playlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.dto.PlaylistInfoDTO

class PlaylistAdapter(
    private val context: Context,
    private val playlistInfoDTOList: List<PlaylistInfoDTO>,
    private val orientation: Int
) : RecyclerView.Adapter<PlaylistViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view: View = if (orientation == LinearLayoutManager.HORIZONTAL) {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_playlist_horizontal, parent, false)
        } else {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_playlist_vertical, parent, false)
        }
        return PlaylistViewHolder(view, orientation)
    }

    override fun getItemCount(): Int {
        return playlistInfoDTOList.size
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val playlistInfoDTO = playlistInfoDTOList[position]

        holder.playlistName.text = playlistInfoDTO.playlist.name

        var ownerName = ""
        ownerName += playlistInfoDTO.owner.joinToString(", ") { it.nickname }

        holder.ownerName.text = ownerName

        if (orientation == LinearLayoutManager.VERTICAL) {
            if (playlistInfoDTO.isAdded.isAdded) {
                holder.addButton?.setImageResource(R.drawable.ic_check)
            } else {
                holder.addButton?.setImageResource(R.drawable.ic_plus)
            }

            when (playlistInfoDTO.rating.name) {
                "Like" -> {
                    holder.likeButton?.setImageResource(R.drawable.ic_thumb_up)
                    holder.dislikeButton?.setImageResource(R.drawable.ic_thumb_down_outline)
                }

                "Dislike" -> {
                    holder.likeButton?.setImageResource(R.drawable.ic_thumb_up_outline)
                    holder.dislikeButton?.setImageResource(R.drawable.ic_thumb_down)
                }

                else -> {
                    holder.likeButton?.setImageResource(R.drawable.ic_thumb_up_outline)
                    holder.dislikeButton?.setImageResource(R.drawable.ic_thumb_down_outline)
                }
            }
        }
    }
}