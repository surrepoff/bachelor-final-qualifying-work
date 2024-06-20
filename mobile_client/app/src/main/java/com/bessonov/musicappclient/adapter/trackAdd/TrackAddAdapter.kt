package com.bessonov.musicappclient.adapter.trackAdd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.dto.TrackInfoDTO
import com.bessonov.musicappclient.manager.ConfigManager
import com.bessonov.musicappclient.manager.SessionManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders

class TrackAddAdapter(
    private val context: Context,
    private val trackInfoDTOList: List<TrackInfoDTO>,
    private val currentTrackIdList: List<Int>,
    private var addTrackIdList: List<Int>,
    private var removeTrackIdList: List<Int>,
    private val onAddTrackClick: (Int, Int) -> Unit
) : RecyclerView.Adapter<TrackAddViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrackAddViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_track_add, parent, false)
        return TrackAddViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trackInfoDTOList.size
    }

    override fun onBindViewHolder(holder: TrackAddViewHolder, position: Int) {
        val trackInfoDTO = trackInfoDTOList[position]

        holder.addButton.setOnClickListener {
            onAddTrackClick.invoke(trackInfoDTO.track.id, position)

            addTrackIdList = if (addTrackIdList.contains(trackInfoDTO.track.id)) {
                addTrackIdList.filter { it != trackInfoDTO.track.id }
            } else {
                addTrackIdList + trackInfoDTO.track.id
            }
        }

        if (addTrackIdList.contains(trackInfoDTO.track.id) ||
            currentTrackIdList.contains(trackInfoDTO.track.id)
        ) {
            holder.addButton.setImageResource(R.drawable.ic_check)
        } else {
            if (removeTrackIdList.contains(trackInfoDTO.track.id)) {
                holder.addButton.setImageResource(R.drawable.ic_trash_can)
            } else {
                holder.addButton.setImageResource(R.drawable.ic_plus)
            }
        }

        holder.trackName.text = trackInfoDTO.track.name

        var artistName = ""
        artistName += trackInfoDTO.artist.joinToString(", ") { it.name }
        if (trackInfoDTO.featuredArtist.size != 0) {
            artistName += " feat. " + trackInfoDTO.featuredArtist.joinToString(", ") { it.name }
        }

        holder.artistName.text = artistName
        holder.trackDuration.text = String.format(
            "%02d:%02d",
            trackInfoDTO.track.durationInSeconds / 60,
            trackInfoDTO.track.durationInSeconds % 60
        )

        val configManager = ConfigManager(context)
        val sessionManager = SessionManager(context)

        val glideUrl = GlideUrl(
            configManager.getServerIp() + "/image/track/" + trackInfoDTO.track.id,
            LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer " + sessionManager.fetchAuthToken())
                .build()
        )

        Glide.with(holder.itemView)
            .load(glideUrl)
            .placeholder(R.drawable.default_album)
            .into(holder.trackImage)
    }

    fun getAddTrackIdList(): List<Int> {
        return addTrackIdList
    }
}