package com.bessonov.musicappclient.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.adapter.section.Section
import com.bessonov.musicappclient.adapter.section.SectionAdapter
import com.bessonov.musicappclient.adapter.section.SectionType
import com.bessonov.musicappclient.dto.AlbumInfoDTO
import com.bessonov.musicappclient.dto.ArtistInfoDTO
import com.bessonov.musicappclient.dto.TrackInfoDTO
import com.bessonov.musicappclient.api.AlbumAPI
import com.bessonov.musicappclient.api.ArtistAPI
import com.bessonov.musicappclient.api.RetrofitClient
import com.bessonov.musicappclient.api.TrackAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var sectionList : List<Section<*>>
    private lateinit var artistInfoDTOList : List<ArtistInfoDTO>
    private lateinit var albumInfoDTOList : List<AlbumInfoDTO>
    private lateinit var trackInfoDTOList : List<TrackInfoDTO>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.fragmentHome_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        artistInfoDTOList = emptyList()
        albumInfoDTOList = emptyList()
        trackInfoDTOList = emptyList()

        return view
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun populateListView() {
        val artistSection : Section<ArtistInfoDTO> = Section<ArtistInfoDTO> (
            title = "Artists",
            type = SectionType.ARTIST,
            items = artistInfoDTOList,
            orientation = LinearLayoutManager.HORIZONTAL
        )

        val albumSection : Section<AlbumInfoDTO> = Section<AlbumInfoDTO> (
            title = "Albums",
            type = SectionType.ALBUM,
            items = albumInfoDTOList,
            orientation = LinearLayoutManager.HORIZONTAL
        )

        val trackSection : Section<TrackInfoDTO> = Section<TrackInfoDTO> (
            title = "Tracks",
            type = SectionType.TRACK,
            items = trackInfoDTOList,
            orientation = LinearLayoutManager.VERTICAL
        )

        sectionList = listOf(artistSection, albumSection, trackSection)

        val sectionAdapter = SectionAdapter(sectionList)
        recyclerView.adapter = sectionAdapter
    }

    private fun loadData() {
        loadArtists()
        loadAlbums()
        loadTracks()
    }

    private fun loadArtists() {
        val retrofitClient = RetrofitClient()
        val artistAPI = retrofitClient.getRetrofit(requireContext()).create(ArtistAPI::class.java)

        artistAPI.getAll().enqueue(object : Callback<List<ArtistInfoDTO>> {
            override fun onResponse(call: Call<List<ArtistInfoDTO>>, response: Response<List<ArtistInfoDTO>>) {
                if (response.isSuccessful && response.body() != null) {
                    artistInfoDTOList = response.body()!!
                    populateListView()
                } else {
                    Toast.makeText(requireContext(), "Failed to load artists (onResponse)", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ArtistInfoDTO>>, t: Throwable) {
                Log.e("LoadArtists", "Failed to load artists", t)
                Toast.makeText(requireContext(), "Failed to load artists (onFailure)", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadAlbums() {
        val retrofitClient = RetrofitClient()
        val albumAPI = retrofitClient.getRetrofit(requireContext()).create(AlbumAPI::class.java)

        albumAPI.getAll().enqueue(object : Callback<List<AlbumInfoDTO>> {
            override fun onResponse(call: Call<List<AlbumInfoDTO>>, response: Response<List<AlbumInfoDTO>>) {
                if (response.isSuccessful && response.body() != null) {
                    albumInfoDTOList = response.body()!!
                    populateListView()
                } else {
                    Toast.makeText(requireContext(), "Failed to load albums (onResponse)", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<AlbumInfoDTO>>, t: Throwable) {
                Log.e("LoadAlbums", "Failed to load albums", t)
                Toast.makeText(requireContext(), "Failed to load albums (onFailure)", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadTracks() {
        val retrofitClient = RetrofitClient()
        val trackAPI = retrofitClient.getRetrofit(requireContext()).create(TrackAPI::class.java)

        trackAPI.getAll().enqueue(object : Callback<List<TrackInfoDTO>> {
            override fun onResponse(call: Call<List<TrackInfoDTO>>, response: Response<List<TrackInfoDTO>>) {
                if (response.isSuccessful && response.body() != null) {
                    trackInfoDTOList = response.body()!!
                    populateListView()
                } else {
                    Toast.makeText(requireContext(), "Failed to load tracks (onResponse)", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<TrackInfoDTO>>, t: Throwable) {
                Log.e("LoadTracks", "Failed to load tracks", t)
                Toast.makeText(requireContext(), "Failed to load tracks (onFailure)", Toast.LENGTH_SHORT).show()
            }
        })
    }
}