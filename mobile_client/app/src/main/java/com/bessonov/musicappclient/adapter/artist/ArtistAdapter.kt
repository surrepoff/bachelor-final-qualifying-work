package com.bessonov.musicappclient.adapter.artist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.dto.ArtistInfoDTO
import com.bessonov.musicappclient.utils.ConfigManager
import com.bumptech.glide.Glide

class ArtistAdapter(
    private val context: Context,
    private val artistInfoDTOList: List<ArtistInfoDTO>
) : RecyclerView.Adapter<ArtistViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_artist_vertical, parent, false)
        return ArtistViewHolder(view)
    }

    override fun getItemCount(): Int {
        return artistInfoDTOList.size
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val artistInfoDTO = artistInfoDTOList[position]

        holder.artistName.text = artistInfoDTO.artist.name

        val configManager = ConfigManager(context);

        Glide.with(holder.itemView)
            .load(configManager.getServerIp() + "api/image/artist/" + artistInfoDTO.artist.id)
            .placeholder(R.drawable.default_artist)
            .into(holder.artistImage)
    }
}