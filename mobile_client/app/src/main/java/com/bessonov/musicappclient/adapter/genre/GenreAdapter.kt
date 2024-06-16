package com.bessonov.musicappclient.adapter.genre

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.dto.GenreDTO

class GenreAdapter(
    private val context: Context,
    private val genreDTOList: List<GenreDTO>
) : RecyclerView.Adapter<GenreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        return GenreViewHolder(view)
    }

    override fun getItemCount(): Int {
        return genreDTOList.size
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genreDTO = genreDTOList[position]
        holder.genreCheckBox.text = genreDTO.name
        holder.genreCheckBox.isChecked = true
    }
}