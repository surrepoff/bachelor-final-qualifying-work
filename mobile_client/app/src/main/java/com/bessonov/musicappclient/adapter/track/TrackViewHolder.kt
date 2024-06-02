package com.bessonov.musicappclient.adapter.track

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R

class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val trackImage: ImageView = itemView.findViewById(R.id.itemTrack_trackImage)
    val trackName: TextView = itemView.findViewById(R.id.itemTrack_trackName)
    val artistName: TextView = itemView.findViewById(R.id.itemTrack_artistName)
    val trackDuration: TextView = itemView.findViewById(R.id.itemTrack_trackDuration)
    val addButton: ImageButton = itemView.findViewById(R.id.itemTrack_addButton)
    val likeButton: ImageButton = itemView.findViewById(R.id.itemTrack_likeButton)
    val dislikeButton: ImageButton = itemView.findViewById(R.id.itemTrack_dislikeButton)
    var clickListener: TrackItemClickListener? = null

    init {
        itemView.setOnClickListener(this)
        addButton.setOnClickListener(this)
        likeButton.setOnClickListener(this)
        dislikeButton.setOnClickListener(this)
    }

    fun bind(clickListener: TrackItemClickListener?) {
        this.clickListener = clickListener
    }

    override fun onClick(view: View) {
        clickListener?.let {
            when (view.id) {
                R.id.itemTrack_addButton -> it.onTrackButtonClick(view, adapterPosition, 1)
                R.id.itemTrack_likeButton -> it.onTrackButtonClick(view, adapterPosition, 2)
                R.id.itemTrack_dislikeButton -> it.onTrackButtonClick(view, adapterPosition, 3)
                else -> it.onTrackItemClick(view, adapterPosition)
            }
        }
    }
}