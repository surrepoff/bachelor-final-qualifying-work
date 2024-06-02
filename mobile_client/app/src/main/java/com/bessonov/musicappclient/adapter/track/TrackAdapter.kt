package com.bessonov.musicappclient.adapter.track

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.dto.TrackInfoDTO
import com.bumptech.glide.Glide
import java.util.Collections

class TrackAdapter(
    private val context: Context,
    private val trackInfoDTOList: List<TrackInfoDTO>,
    private val trackItemClickListener: TrackItemClickListener
) : RecyclerView.Adapter<TrackViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_track, parent, false)
        return TrackViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trackInfoDTOList.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val trackInfoDTO = trackInfoDTOList[position]

        holder.trackName.text = trackInfoDTO.track.name

        var artistName : String = ""
        artistName += trackInfoDTO.artist.joinToString(", ") {it.name}
        if (trackInfoDTO.featuredArtist.size != 0) {
            artistName += " feat. " + trackInfoDTO.featuredArtist.joinToString(", ") {it.name}
        }

        holder.artistName.text = artistName
        holder.trackDuration.text = String.format("%02d:%02d", trackInfoDTO.track.durationInSeconds / 60, trackInfoDTO.track.durationInSeconds % 60)

        holder.bind(trackItemClickListener)

        Glide.with(holder.itemView)
            .load("http://192.168.1.59:8080/api/image/track/" + trackInfoDTO.track.id)
            .placeholder(R.drawable.default_album)
            .into(holder.trackImage)
    }

    fun swapItems(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(trackInfoDTOList, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(trackInfoDTOList, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }
}