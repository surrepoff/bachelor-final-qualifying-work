package com.bessonov.musicappclient.adapter.track

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.dto.TrackInfoDTO

class TrackAdapter(private val trackInfoDTOList: List<TrackInfoDTO>) : RecyclerView.Adapter<TrackHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_track, parent, false)
        return TrackHolder(view)
    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        val trackInfoDTO = trackInfoDTOList[position]

        holder.trackName.text = trackInfoDTO.track.name

        var artistName : String = ""
        artistName += trackInfoDTO.artist.joinToString(", ") {it.name}
        if (trackInfoDTO.featuredArtist.size != 0) {
            artistName += " feat. " + trackInfoDTO.featuredArtist.joinToString(", ") {it.name}
        }

        holder.artistName.text = artistName
        holder.trackDuration.text = String.format("%02d:%02d", trackInfoDTO.track.durationInSeconds / 60, trackInfoDTO.track.durationInSeconds % 60)
    }

    override fun getItemCount(): Int {
        return trackInfoDTOList.size
    }
}