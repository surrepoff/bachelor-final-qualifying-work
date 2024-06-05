package com.bessonov.musicappclient.adapter.album

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
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
    private val albumInfoDTOList: List<AlbumInfoDTO>
) : RecyclerView.Adapter<AlbumViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_album_vertical, parent, false)
        return AlbumViewHolder(view)
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
    }
}