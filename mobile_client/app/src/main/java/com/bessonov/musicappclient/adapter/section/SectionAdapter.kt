package com.bessonov.musicappclient.adapter.section

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.adapter.album.AlbumAdapter
import com.bessonov.musicappclient.adapter.artist.ArtistAdapter
import com.bessonov.musicappclient.adapter.track.TrackAdapter
import com.bessonov.musicappclient.dto.AlbumInfoDTO
import com.bessonov.musicappclient.dto.ArtistInfoDTO
import com.bessonov.musicappclient.dto.TrackInfoDTO

class SectionAdapter(private val sectionList: List<Section<*>>) : RecyclerView.Adapter<SectionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_section, parent, false)
        return SectionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sectionList.size
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val section : Section<*> = sectionList[position]

        holder.header.text = section.title;

        holder.recyclerView.layoutManager = if (section.orientation == LinearLayoutManager.HORIZONTAL) {
            LinearLayoutManager(holder.recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
        } else {
            LinearLayoutManager(holder.recyclerView.context, LinearLayoutManager.VERTICAL, false)
        }

        when (section.type) {
            SectionType.ALBUM -> {
                val albumInfoDTOList = section.items.filterIsInstance<AlbumInfoDTO>()
                val albumAdapter = AlbumAdapter(albumInfoDTOList)
                holder.recyclerView.adapter = albumAdapter
            }
            SectionType.ARTIST -> {
                val artistInfoDTOList = section.items.filterIsInstance<ArtistInfoDTO>()
                val artistAdapter = ArtistAdapter(artistInfoDTOList)
                holder.recyclerView.adapter = artistAdapter
            }
            SectionType.TRACK -> {
                val trackInfoDTOList = section.items.filterIsInstance<TrackInfoDTO>()
                val trackAdapter = TrackAdapter(trackInfoDTOList)
                holder.recyclerView.adapter = trackAdapter
            }
            else -> {

            }
        }
    }
}