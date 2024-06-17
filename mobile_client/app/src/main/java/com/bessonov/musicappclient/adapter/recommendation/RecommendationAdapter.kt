package com.bessonov.musicappclient.adapter.recommendation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.dto.RecommendationInfoDTO
import com.bessonov.musicappclient.utils.ButtonType

class RecommendationAdapter(
    private val context: Context,
    private val recommendationInfoDTOList: List<RecommendationInfoDTO>,
    private val orientation: Int,
    private val onItemClick: (ButtonType, Any) -> Unit
) : RecyclerView.Adapter<RecommendationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val view: View = if (orientation == LinearLayoutManager.HORIZONTAL) {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recommendation_horizontal, parent, false)
        } else {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recommendation_vertical, parent, false)
        }
        return RecommendationViewHolder(view, orientation)
    }

    override fun getItemCount(): Int {
        return recommendationInfoDTOList.size
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        val recommendationInfoDTO = recommendationInfoDTOList[position]

        holder.itemView.setOnClickListener {
            onItemClick.invoke(ButtonType.ITEM, recommendationInfoDTO)
        }

        val recommendationName = "Rec №" + recommendationInfoDTO.recommendation.id
        holder.recommendationName.text = recommendationName

        holder.ownerName.text = recommendationInfoDTO.user.nickname

        if (orientation == LinearLayoutManager.VERTICAL) {
            holder.likeButton?.setOnClickListener {
                onItemClick.invoke(ButtonType.LIKE, recommendationInfoDTO)
            }

            holder.dislikeButton?.setOnClickListener {
                onItemClick.invoke(ButtonType.DISLIKE, recommendationInfoDTO)
            }

            when (recommendationInfoDTO.rating.name) {
                "Like" -> {
                    holder.likeButton?.setImageResource(R.drawable.ic_thumb_up)
                    holder.dislikeButton?.setImageResource(R.drawable.ic_thumb_down_outline)
                }

                "Dislike" -> {
                    holder.likeButton?.setImageResource(R.drawable.ic_thumb_up_outline)
                    holder.dislikeButton?.setImageResource(R.drawable.ic_thumb_down)
                }

                else -> {
                    holder.likeButton?.setImageResource(R.drawable.ic_thumb_up_outline)
                    holder.dislikeButton?.setImageResource(R.drawable.ic_thumb_down_outline)
                }
            }
        }
    }
}