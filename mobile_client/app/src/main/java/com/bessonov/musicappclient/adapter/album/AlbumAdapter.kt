package com.bessonov.musicappclient.adapter.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.dto.AlbumInfoDTO
import com.bumptech.glide.Glide

class AlbumAdapter(private val albumInfoDTOList: List<AlbumInfoDTO>) : RecyclerView.Adapter<AlbumViewHolder>() {
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

        var artistName : String = ""
        artistName += albumInfoDTO.artist.joinToString(", ") {it.name}
        if (albumInfoDTO.featuredArtist.size != 0) {
            artistName += " feat. " + albumInfoDTO.featuredArtist.joinToString(", ") {it.name}
        }

        holder.artistName.text = artistName

        Glide.with(holder.itemView)
            .load("http://192.168.1.59:8080/api/image/album/" + albumInfoDTO.album.id)
            .placeholder(R.drawable.default_album)
            .into(holder.albumImage)
    }
}