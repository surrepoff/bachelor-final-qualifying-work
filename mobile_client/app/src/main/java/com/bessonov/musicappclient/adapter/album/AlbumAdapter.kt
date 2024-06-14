package com.bessonov.musicappclient.adapter.album

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.api.SessionManager
import com.bessonov.musicappclient.dto.AlbumInfoDTO
import com.bessonov.musicappclient.utils.ConfigManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders

class AlbumAdapter(
    private val context: Context,
    private val albumInfoDTOList: List<AlbumInfoDTO>,
    private val orientation: Int
) : RecyclerView.Adapter<AlbumViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view: View = if (orientation == LinearLayoutManager.HORIZONTAL) {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_album_horizontal, parent, false)
        } else {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_album_vertical, parent, false)
        }
        return AlbumViewHolder(view, orientation)
    }

    override fun getItemCount(): Int {
        return albumInfoDTOList.size
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val albumInfoDTO = albumInfoDTOList[position]

        holder.albumName.text = albumInfoDTO.album.name

        var artistName = ""
        artistName += albumInfoDTO.artist.joinToString(", ") {it.name}
        if (albumInfoDTO.featuredArtist.size != 0) {
            artistName += " feat. " + albumInfoDTO.featuredArtist.joinToString(", ") {it.name}
        }

        holder.artistName.text = artistName

        val configManager = ConfigManager(context)
        val sessionManager = SessionManager(context)

        val glideUrl = GlideUrl(
            configManager.getServerIp() + "/image/album/" + albumInfoDTO.album.id,
            LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer " + sessionManager.fetchAuthToken())
                .build()
        )

        Glide.with(holder.itemView)
            .load(glideUrl)
            .placeholder(R.drawable.default_album)
            .into(holder.albumImage)

        if (orientation == LinearLayoutManager.VERTICAL) {
            if (albumInfoDTO.isAdded.isAdded) {
                holder.addButton?.setImageResource(R.drawable.ic_check)
            }
            else {
                holder.addButton?.setImageResource(R.drawable.ic_plus)
            }

            when (albumInfoDTO.rating.name) {
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