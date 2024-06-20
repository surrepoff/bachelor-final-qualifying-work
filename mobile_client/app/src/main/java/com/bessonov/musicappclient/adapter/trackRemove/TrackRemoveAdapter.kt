package com.bessonov.musicappclient.adapter.trackRemove

import android.annotation.SuppressLint
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

class TrackRemoveAdapter(
    private val context: Context,
    private val trackInfoDTOList: List<TrackInfoDTO>,
    private val onRemoveTrackClick: (Int, Int) -> Unit
) : RecyclerView.Adapter<TrackRemoveViewHolder>() {
    private var remove: Boolean = false
    private var removeTrackIdList: List<Int> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrackRemoveViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_track_remove, parent, false)
        return TrackRemoveViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trackInfoDTOList.size
    }

    override fun onBindViewHolder(holder: TrackRemoveViewHolder, position: Int) {
        val trackInfoDTO = trackInfoDTOList[position]

        holder.removeButton.setOnClickListener {
            onRemoveTrackClick.invoke(trackInfoDTO.track.id, position)

            removeTrackIdList = if (removeTrackIdList.contains(trackInfoDTO.track.id)) {
                removeTrackIdList.filter { it != trackInfoDTO.track.id }
            } else {
                removeTrackIdList + trackInfoDTO.track.id
            }
        }

        if (removeTrackIdList.contains(trackInfoDTO.track.id)) {
            holder.removeButton.setImageResource(R.drawable.ic_trash_can)
        } else {
            holder.removeButton.setImageResource(R.drawable.ic_trash_can_outline)
        }

        if (remove || removeTrackIdList.contains(trackInfoDTO.track.id))
            holder.removeButton.visibility = View.VISIBLE
        else
            holder.removeButton.visibility = View.INVISIBLE

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

    @SuppressLint("NotifyDataSetChanged")
    fun changeMode(remove: Boolean) {
        this.remove = remove
        notifyDataSetChanged()
    }
}