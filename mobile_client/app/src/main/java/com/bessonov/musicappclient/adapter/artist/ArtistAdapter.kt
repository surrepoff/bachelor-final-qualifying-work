package com.bessonov.musicappclient.adapter.artist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.dto.ArtistInfoDTO
import com.bessonov.musicappclient.manager.ConfigManager
import com.bessonov.musicappclient.manager.SessionManager
import com.bessonov.musicappclient.utils.ButtonType
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders


class ArtistAdapter(
    private val context: Context,
    private var artistInfoDTOList: List<ArtistInfoDTO>,
    private val orientation: Int,
    private val onItemClick: (ButtonType, Any, Int) -> Unit
) : RecyclerView.Adapter<ArtistViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val view: View = if (orientation == LinearLayoutManager.HORIZONTAL) {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_artist_horizontal, parent, false)
        } else {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_artist_vertical, parent, false)
        }
        return ArtistViewHolder(view, orientation)
    }

    override fun getItemCount(): Int {
        return artistInfoDTOList.size
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val artistInfoDTO = artistInfoDTOList[position]

        holder.itemView.setOnClickListener {
            onItemClick.invoke(ButtonType.ITEM, artistInfoDTO, position)
        }

        holder.artistName.text = artistInfoDTO.artist.name

        val configManager = ConfigManager(context)
        val sessionManager = SessionManager(context)

        val glideUrl = GlideUrl(
            configManager.getServerIp() + "/image/artist/" + artistInfoDTO.artist.id,
            LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer " + sessionManager.fetchAuthToken())
                .build()
        )

        Glide.with(holder.itemView)
            .load(glideUrl)
            .placeholder(R.drawable.default_artist)
            .into(holder.artistImage)

        if (orientation == LinearLayoutManager.VERTICAL) {
            holder.addButton?.setOnClickListener {
                onItemClick.invoke(ButtonType.ADD, artistInfoDTO, position)
            }

            if (artistInfoDTO.isAdded.isAdded) {
                holder.addButton?.setImageResource(R.drawable.ic_check)
            } else {
                holder.addButton?.setImageResource(R.drawable.ic_plus)
            }

            holder.likeButton?.setOnClickListener {
                onItemClick.invoke(ButtonType.LIKE, artistInfoDTO, position)
            }

            holder.dislikeButton?.setOnClickListener {
                onItemClick.invoke(ButtonType.DISLIKE, artistInfoDTO, position)
            }

            when (artistInfoDTO.rating.name) {
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

    fun updateItem(position: Int, artistInfoDTO: ArtistInfoDTO) {
        val artistInfoDTOMutableList = artistInfoDTOList.toMutableList()
        artistInfoDTOMutableList[position] = artistInfoDTO
        artistInfoDTOList = artistInfoDTOMutableList.toList()
        notifyItemChanged(position)
    }
}