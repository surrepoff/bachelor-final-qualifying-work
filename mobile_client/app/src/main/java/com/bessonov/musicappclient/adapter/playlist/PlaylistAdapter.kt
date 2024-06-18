package com.bessonov.musicappclient.adapter.playlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.dto.PlaylistInfoDTO
import com.bessonov.musicappclient.utils.ButtonType

class PlaylistAdapter(
    private val context: Context,
    private var playlistInfoDTOList: List<PlaylistInfoDTO>,
    private val orientation: Int,
    private val onItemClick: (ButtonType, Any, Int) -> Unit
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

        holder.itemView.setOnClickListener {
            onItemClick.invoke(ButtonType.ITEM, playlistInfoDTO, position)
        }

        holder.playlistName.text = playlistInfoDTO.playlist.name

        var ownerName = ""
        ownerName += playlistInfoDTO.owner.joinToString(", ") { it.nickname }

        holder.ownerName.text = ownerName

        if (orientation == LinearLayoutManager.VERTICAL) {
            holder.addButton?.setOnClickListener {
                onItemClick.invoke(ButtonType.ADD, playlistInfoDTO, position)
            }

            if (playlistInfoDTO.isAdded.isAdded) {
                holder.addButton?.setImageResource(R.drawable.ic_check)
            } else {
                holder.addButton?.setImageResource(R.drawable.ic_plus)
            }

            holder.likeButton?.setOnClickListener {
                onItemClick.invoke(ButtonType.LIKE, playlistInfoDTO, position)
            }

            holder.dislikeButton?.setOnClickListener {
                onItemClick.invoke(ButtonType.DISLIKE, playlistInfoDTO, position)
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

    fun updateItem(position: Int, playlistInfoDTO: PlaylistInfoDTO) {
        val playlistInfoDTOMutableList = playlistInfoDTOList.toMutableList()
        playlistInfoDTOMutableList[position] = playlistInfoDTO
        playlistInfoDTOList = playlistInfoDTOMutableList.toList()
        notifyItemChanged(position)
    }
}