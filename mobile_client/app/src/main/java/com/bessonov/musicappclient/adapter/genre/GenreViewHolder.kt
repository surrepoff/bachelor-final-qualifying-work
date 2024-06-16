package com.bessonov.musicappclient.adapter.genre

import android.view.View
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R

class GenreViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    val genreCheckBox: CheckBox = itemView.findViewById(R.id.itemGenre_genreCheckBox)
}