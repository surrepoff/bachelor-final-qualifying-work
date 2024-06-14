package com.bessonov.musicappclient.ui.section

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.adapter.album.AlbumAdapter
import com.bessonov.musicappclient.adapter.artist.ArtistAdapter
import com.bessonov.musicappclient.adapter.playlist.PlaylistAdapter
import com.bessonov.musicappclient.adapter.section.Section
import com.bessonov.musicappclient.adapter.section.SectionType
import com.bessonov.musicappclient.adapter.track.DragManageAdapter
import com.bessonov.musicappclient.adapter.track.TrackAdapter
import com.bessonov.musicappclient.adapter.track.TrackItemClickListener
import com.bessonov.musicappclient.api.AlbumAPI
import com.bessonov.musicappclient.api.ArtistAPI
import com.bessonov.musicappclient.api.PlaylistAPI
import com.bessonov.musicappclient.api.RetrofitClient
import com.bessonov.musicappclient.api.TrackAPI
import com.bessonov.musicappclient.dto.AlbumInfoDTO
import com.bessonov.musicappclient.dto.ArtistInfoDTO
import com.bessonov.musicappclient.dto.PlaylistInfoDTO
import com.bessonov.musicappclient.dto.TrackInfoDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SectionFragment(
    private val section: Section<*>
) : Fragment(), TrackItemClickListener {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var sectionName: TextView
    private lateinit var closeButton: ImageButton
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_section, container, false)

        swipeRefreshLayout = view.findViewById(R.id.fragmentSection_swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }

        closeButton = view.findViewById(R.id.fragmentSection_closeButton)
        closeButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        sectionName = view.findViewById(R.id.fragmentSection_sectionName)
        sectionName.text = section.title

        recyclerView = view.findViewById(R.id.fragmentSection_recyclerView)

        recyclerView.layoutManager =
            LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)
        populateListView()

        return view
    }

    private fun populateListView() {
        when (section.type) {
            SectionType.ALBUM -> {
                val albumInfoDTOList = section.items.filterIsInstance<AlbumInfoDTO>()
                val albumAdapter =
                    AlbumAdapter(requireContext(), albumInfoDTOList, LinearLayoutManager.VERTICAL)
                recyclerView.adapter = albumAdapter
            }

            SectionType.ARTIST -> {
                val artistInfoDTOList = section.items.filterIsInstance<ArtistInfoDTO>()
                val artistAdapter =
                    ArtistAdapter(requireContext(), artistInfoDTOList, LinearLayoutManager.VERTICAL)
                recyclerView.adapter = artistAdapter
            }

            SectionType.PLAYLIST -> {
                val playlistInfoDTOList = section.items.filterIsInstance<PlaylistInfoDTO>()
                val playlistAdapter = PlaylistAdapter(
                    requireContext(),
                    playlistInfoDTOList,
                    LinearLayoutManager.VERTICAL
                )
                recyclerView.adapter = playlistAdapter
            }

            SectionType.TRACK -> {
                val trackInfoDTOList = section.items.filterIsInstance<TrackInfoDTO>()
                val trackAdapter =
                    TrackAdapter(requireContext(), trackInfoDTOList, this@SectionFragment)
                recyclerView.adapter = trackAdapter

                val itemTouchHelper = ItemTouchHelper(DragManageAdapter(trackAdapter))
                itemTouchHelper.attachToRecyclerView(recyclerView)
            }
        }
        swipeRefreshLayout.isRefreshing = false
    }

    private fun loadData() {
        when (section.title) {
            "Artists" -> {
                loadArtists()
            }

            "My Artists" -> {
                loadMyArtists()
            }

            "Albums" -> {
                loadAlbums()
            }

            "My Albums" -> {
                loadMyAlbums()
            }

            "My Playlists" -> {
                loadMyPlaylists()
            }

            "Tracks" -> {
                loadTracks()
            }

            "My Tracks" -> {
                loadMyTracks()
            }
        }
    }

    private fun loadArtists() {
        val retrofitClient = RetrofitClient()
        val artistAPI = retrofitClient.getRetrofit(requireContext()).create(ArtistAPI::class.java)

        artistAPI.getAll().enqueue(object : Callback<List<ArtistInfoDTO>> {
            override fun onResponse(
                call: Call<List<ArtistInfoDTO>>,
                response: Response<List<ArtistInfoDTO>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    section.items = (response.body() as List<Nothing>?)!!
                    populateListView()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Failed to load artists (onResponse)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<ArtistInfoDTO>>, t: Throwable) {
                Log.e("LoadArtists", "Failed to load artists", t)
                Toast.makeText(
                    requireContext(),
                    "Failed to load artists (onFailure)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun loadMyArtists() {
        val retrofitClient = RetrofitClient()
        val artistAPI = retrofitClient.getRetrofit(requireContext()).create(ArtistAPI::class.java)

        artistAPI.getAllUser().enqueue(object : Callback<List<ArtistInfoDTO>> {
            override fun onResponse(
                call: Call<List<ArtistInfoDTO>>,
                response: Response<List<ArtistInfoDTO>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    section.items = (response.body() as List<Nothing>?)!!
                    populateListView()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Failed to load artists (onResponse)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<ArtistInfoDTO>>, t: Throwable) {
                Log.e("LoadArtists", "Failed to load artists", t)
                Toast.makeText(
                    requireContext(),
                    "Failed to load artists (onFailure)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun loadAlbums() {
        val retrofitClient = RetrofitClient()
        val albumAPI = retrofitClient.getRetrofit(requireContext()).create(AlbumAPI::class.java)

        albumAPI.getAll().enqueue(object : Callback<List<AlbumInfoDTO>> {
            override fun onResponse(
                call: Call<List<AlbumInfoDTO>>,
                response: Response<List<AlbumInfoDTO>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    section.items = (response.body() as List<Nothing>?)!!
                    populateListView()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Failed to load albums (onResponse)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<AlbumInfoDTO>>, t: Throwable) {
                Log.e("LoadAlbums", "Failed to load albums", t)
                Toast.makeText(
                    requireContext(),
                    "Failed to load albums (onFailure)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun loadMyAlbums() {
        val retrofitClient = RetrofitClient()
        val albumAPI = retrofitClient.getRetrofit(requireContext()).create(AlbumAPI::class.java)

        albumAPI.getAllUser().enqueue(object : Callback<List<AlbumInfoDTO>> {
            override fun onResponse(
                call: Call<List<AlbumInfoDTO>>,
                response: Response<List<AlbumInfoDTO>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    section.items = (response.body() as List<Nothing>?)!!
                    populateListView()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Failed to load albums (onResponse)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<AlbumInfoDTO>>, t: Throwable) {
                Log.e("LoadAlbums", "Failed to load albums", t)
                Toast.makeText(
                    requireContext(),
                    "Failed to load albums (onFailure)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun loadMyPlaylists() {
        val retrofitClient = RetrofitClient()
        val playlistAPI =
            retrofitClient.getRetrofit(requireContext()).create(PlaylistAPI::class.java)

        playlistAPI.getAllUser().enqueue(object : Callback<List<PlaylistInfoDTO>> {
            override fun onResponse(
                call: Call<List<PlaylistInfoDTO>>,
                response: Response<List<PlaylistInfoDTO>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    section.items = (response.body() as List<Nothing>?)!!
                    populateListView()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Failed to load playlists (onResponse)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<PlaylistInfoDTO>>, t: Throwable) {
                Log.e("LoadPlaylists", "Failed to load playlists", t)
                Toast.makeText(
                    requireContext(),
                    "Failed to load playlists (onFailure)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun loadTracks() {
        val retrofitClient = RetrofitClient()
        val trackAPI = retrofitClient.getRetrofit(requireContext()).create(TrackAPI::class.java)

        trackAPI.getAll().enqueue(object : Callback<List<TrackInfoDTO>> {
            override fun onResponse(
                call: Call<List<TrackInfoDTO>>,
                response: Response<List<TrackInfoDTO>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    section.items = (response.body() as List<Nothing>?)!!
                    populateListView()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Failed to load tracks (onResponse)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<TrackInfoDTO>>, t: Throwable) {
                Log.e("LoadTracks", "Failed to load tracks", t)
                Toast.makeText(
                    requireContext(),
                    "Failed to load tracks (onFailure)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun loadMyTracks() {
        val retrofitClient = RetrofitClient()
        val trackAPI = retrofitClient.getRetrofit(requireContext()).create(TrackAPI::class.java)

        trackAPI.getAllUser().enqueue(object : Callback<List<TrackInfoDTO>> {
            override fun onResponse(
                call: Call<List<TrackInfoDTO>>,
                response: Response<List<TrackInfoDTO>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    section.items = (response.body() as List<Nothing>?)!!
                    populateListView()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Failed to load tracks (onResponse)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<TrackInfoDTO>>, t: Throwable) {
                Log.e("LoadTracks", "Failed to load tracks", t)
                Toast.makeText(
                    requireContext(),
                    "Failed to load tracks (onFailure)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onTrackItemClick(view: View, position: Int) {
        Toast.makeText(context, "Track item clicked at position $position", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onTrackButtonClick(view: View, position: Int, buttonId: Int) {
        when (buttonId) {
            1 -> Toast.makeText(
                context,
                "Track Add Button clicked at position $position",
                Toast.LENGTH_SHORT
            ).show()

            2 -> Toast.makeText(
                context,
                "Track Like Button clicked at position $position",
                Toast.LENGTH_SHORT
            ).show()

            3 -> Toast.makeText(
                context,
                "Track Dislike Button clicked at position $position",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}