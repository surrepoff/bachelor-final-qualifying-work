package com.bessonov.musicappclient.adapter.section

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.adapter.album.AlbumAdapter
import com.bessonov.musicappclient.adapter.artist.ArtistAdapter
import com.bessonov.musicappclient.adapter.playlist.PlaylistAdapter
import com.bessonov.musicappclient.adapter.playlistCreate.PlaylistCreateAdapter
import com.bessonov.musicappclient.adapter.recommendation.RecommendationAdapter
import com.bessonov.musicappclient.adapter.recommendationCreate.RecommendationCreateAdapter
import com.bessonov.musicappclient.adapter.track.DragManageAdapter
import com.bessonov.musicappclient.adapter.track.TrackAdapter
import com.bessonov.musicappclient.dto.AlbumInfoDTO
import com.bessonov.musicappclient.dto.ArtistInfoDTO
import com.bessonov.musicappclient.dto.PlaylistInfoDTO
import com.bessonov.musicappclient.dto.RecommendationInfoDTO
import com.bessonov.musicappclient.dto.TrackInfoDTO
import com.bessonov.musicappclient.utils.ButtonType
import com.bessonov.musicappclient.utils.ItemType

class SectionAdapter(
    private val context: Context,
    private val sectionList: List<Section<*>>,
    private val onSectionClick: (Section<*>) -> Unit,
    private val onItemClick: (ItemType, Int, ButtonType, Any, Int) -> Unit
) : RecyclerView.Adapter<SectionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_section, parent, false)
        return SectionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sectionList.size
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val section: Section<*> = sectionList[position]

        holder.header.text = section.title
        holder.header.setOnClickListener {
            onSectionClick.invoke(sectionList[position])
        }

        holder.recyclerView.layoutManager =
            if (section.orientation == LinearLayoutManager.HORIZONTAL) {
                LinearLayoutManager(
                    holder.recyclerView.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            } else {
                LinearLayoutManager(
                    holder.recyclerView.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }

        when (section.type) {
            SectionType.ALBUM -> {
                val albumInfoDTOList = section.items.filterIsInstance<AlbumInfoDTO>()
                val albumAdapter =
                    AlbumAdapter(
                        context,
                        albumInfoDTOList,
                        section.orientation
                    ) { buttonType, any, itemPosition ->
                        onItemClick.invoke(ItemType.ALBUM, position, buttonType, any, itemPosition)
                    }
                holder.recyclerView.adapter = albumAdapter
            }

            SectionType.ARTIST -> {
                val artistInfoDTOList = section.items.filterIsInstance<ArtistInfoDTO>()
                val artistAdapter = ArtistAdapter(
                    context,
                    artistInfoDTOList,
                    section.orientation
                ) { buttonType, any, itemPosition ->
                    onItemClick.invoke(ItemType.ARTIST, position, buttonType, any, itemPosition)
                }
                holder.recyclerView.adapter = artistAdapter
            }

            SectionType.PLAYLIST -> {
                val playlistInfoDTOList = section.items.filterIsInstance<PlaylistInfoDTO>()
                val playlistAdapter =
                    PlaylistAdapter(
                        context,
                        playlistInfoDTOList,
                        section.orientation
                    ) { buttonType, any, itemPosition ->
                        onItemClick.invoke(
                            ItemType.PLAYLIST,
                            position,
                            buttonType,
                            any,
                            itemPosition
                        )
                    }
                holder.recyclerView.adapter = playlistAdapter
            }

            SectionType.PLAYLIST_CREATE -> {
                val playlistCreateList = section.items.filterIsInstance<String>()
                val playlistCreateAdapter =
                    PlaylistCreateAdapter(context, playlistCreateList) {
                        onSectionClick.invoke(sectionList[position])
                    }
                holder.header.visibility = View.GONE
                holder.recyclerView.adapter = playlistCreateAdapter
            }

            SectionType.RECOMMENDATION -> {
                val recommendationInfoDTOList =
                    section.items.filterIsInstance<RecommendationInfoDTO>()
                val recommendationAdapter =
                    RecommendationAdapter(
                        context,
                        recommendationInfoDTOList,
                        section.orientation
                    ) { buttonType, any, itemPosition ->
                        onItemClick.invoke(
                            ItemType.RECOMMENDATION,
                            position,
                            buttonType,
                            any,
                            itemPosition
                        )
                    }
                holder.recyclerView.adapter = recommendationAdapter
            }

            SectionType.RECOMMENDATION_CREATE -> {
                val recommendationCreateList = section.items.filterIsInstance<String>()
                val recommendationCreateAdapter =
                    RecommendationCreateAdapter(context, recommendationCreateList) {
                        onSectionClick.invoke(sectionList[position])
                    }
                holder.header.visibility = View.GONE
                holder.recyclerView.adapter = recommendationCreateAdapter
            }

            SectionType.TRACK -> {
                val trackInfoDTOList = section.items.filterIsInstance<TrackInfoDTO>()
                val trackAdapter =
                    TrackAdapter(context, trackInfoDTOList) { buttonType, any, itemPosition ->
                        onItemClick.invoke(ItemType.TRACK, position, buttonType, any, itemPosition)
                    }
                holder.recyclerView.adapter = trackAdapter

                val itemTouchHelper = ItemTouchHelper(DragManageAdapter(trackAdapter))
                itemTouchHelper.attachToRecyclerView(holder.recyclerView)
            }
        }
    }
}