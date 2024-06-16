package com.bessonov.musicappclient.adapter.recommendationCreate

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R

class RecommendationCreateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val createButton: Button = itemView.findViewById(R.id.itemRecommendationCreate_createButton)
}