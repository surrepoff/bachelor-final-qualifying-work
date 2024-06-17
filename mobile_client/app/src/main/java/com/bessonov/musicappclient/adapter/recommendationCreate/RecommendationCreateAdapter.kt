package com.bessonov.musicappclient.adapter.recommendationCreate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R

class RecommendationCreateAdapter(
    private val context: Context,
    private val recommendationCreateList: List<String>,
    private val onButtonClick: () -> Unit
) : RecyclerView.Adapter<RecommendationCreateViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendationCreateViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recommendation_create, parent, false)
        return RecommendationCreateViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recommendationCreateList.size
    }

    override fun onBindViewHolder(holder: RecommendationCreateViewHolder, position: Int) {
        val recommendationCreate = recommendationCreateList[position]

        holder.createButton.setOnClickListener {
            onButtonClick.invoke()
        }
    }
}