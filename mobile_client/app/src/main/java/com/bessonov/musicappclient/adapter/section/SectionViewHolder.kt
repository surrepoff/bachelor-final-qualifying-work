package com.bessonov.musicappclient.adapter.section

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R

class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val header : TextView = itemView.findViewById(R.id.itemSection_Header)
    val recyclerView : RecyclerView = itemView.findViewById(R.id.itemSection_RecyclerView)
}