package com.bessonov.musicappclient.adapter.recommendation

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R

class RecommendationViewHolder(
    itemView: View,
    orientation: Int
) : RecyclerView.ViewHolder(itemView) {
    val recommendationName: TextView
    val recommendationImage: ImageView
    val ownerName: TextView
    val likeButton: ImageButton?
    val dislikeButton: ImageButton?

    init {
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            recommendationName =
                itemView.findViewById(R.id.itemRecommendationHorizontal_recommendationName)
            recommendationImage =
                itemView.findViewById(R.id.itemRecommendationHorizontal_recommendationImage)
            ownerName = itemView.findViewById(R.id.itemRecommendationHorizontal_ownerName)
            likeButton = null
            dislikeButton = null
        } else {
            recommendationName =
                itemView.findViewById(R.id.itemRecommendationVertical_recommendationName)
            recommendationImage =
                itemView.findViewById(R.id.itemRecommendationVertical_recommendationImage)
            ownerName = itemView.findViewById(R.id.itemRecommendationVertical_ownerName)
            likeButton = itemView.findViewById(R.id.itemRecommendationVertical_likeButton)
            dislikeButton = itemView.findViewById(R.id.itemRecommendationVertical_dislikeButton)
        }
    }
}