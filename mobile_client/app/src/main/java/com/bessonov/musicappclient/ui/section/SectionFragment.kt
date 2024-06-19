package com.bessonov.musicappclient.ui.section

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.adapter.album.AlbumAdapter
import com.bessonov.musicappclient.adapter.artist.ArtistAdapter
import com.bessonov.musicappclient.adapter.playlist.PlaylistAdapter
import com.bessonov.musicappclient.adapter.recommendation.RecommendationAdapter
import com.bessonov.musicappclient.adapter.section.Section
import com.bessonov.musicappclient.adapter.section.SectionType
import com.bessonov.musicappclient.adapter.track.TrackAdapter
import com.bessonov.musicappclient.api.AlbumAPI
import com.bessonov.musicappclient.api.ArtistAPI
import com.bessonov.musicappclient.api.PlaylistAPI
import com.bessonov.musicappclient.api.RecommendationAPI
import com.bessonov.musicappclient.api.SearchAPI
import com.bessonov.musicappclient.api.TrackAPI
import com.bessonov.musicappclient.dto.AlbumInfoDTO
import com.bessonov.musicappclient.dto.ArtistInfoDTO
import com.bessonov.musicappclient.dto.PlaylistInfoDTO
import com.bessonov.musicappclient.dto.RecommendationInfoDTO
import com.bessonov.musicappclient.dto.SearchInfoDTO
import com.bessonov.musicappclient.dto.SearchRequestDTO
import com.bessonov.musicappclient.dto.TrackInfoDTO
import com.bessonov.musicappclient.ui.main.MainActivity
import com.bessonov.musicappclient.utils.ItemClickHandler
import com.bessonov.musicappclient.utils.ItemType
import com.bessonov.musicappclient.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SectionFragment(
    private val section: Section<*>
) : Fragment() {
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

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun populateListView() {
        val itemClickHandler =
            ItemClickHandler(activity as MainActivity, requireContext(), recyclerView)
        when (section.type) {
            SectionType.ALBUM -> {
                val albumInfoDTOList = section.items.filterIsInstance<AlbumInfoDTO>()
                val albumAdapter =
                    AlbumAdapter(
                        requireContext(),
                        albumInfoDTOList,
                        LinearLayoutManager.VERTICAL
                    ) { buttonType, any, itemPosition ->
                        itemClickHandler.onItemClick(
                            ItemType.ALBUM,
                            -1,
                            buttonType,
                            any,
                            itemPosition
                        )
                    }
                recyclerView.adapter = albumAdapter
            }

            SectionType.ARTIST -> {
                val artistInfoDTOList = section.items.filterIsInstance<ArtistInfoDTO>()
                val artistAdapter =
                    ArtistAdapter(
                        requireContext(),
                        artistInfoDTOList,
                        LinearLayoutManager.VERTICAL
                    ) { buttonType, any, itemPosition ->
                        itemClickHandler.onItemClick(
                            ItemType.ARTIST,
                            -1,
                            buttonType,
                            any,
                            itemPosition
                        )
                    }
                recyclerView.adapter = artistAdapter
            }

            SectionType.PLAYLIST -> {
                val playlistInfoDTOList = section.items.filterIsInstance<PlaylistInfoDTO>()
                val playlistAdapter = PlaylistAdapter(
                    requireContext(),
                    playlistInfoDTOList,
                    LinearLayoutManager.VERTICAL
                ) { buttonType, any, itemPosition ->
                    itemClickHandler.onItemClick(
                        ItemType.PLAYLIST,
                        -1,
                        buttonType,
                        any,
                        itemPosition
                    )
                }
                recyclerView.adapter = playlistAdapter
            }

            SectionType.RECOMMENDATION -> {
                val recommendationInfoDTOList =
                    section.items.filterIsInstance<RecommendationInfoDTO>()
                val recommendationAdapter =
                    RecommendationAdapter(
                        requireContext(),
                        recommendationInfoDTOList,
                        LinearLayoutManager.VERTICAL
                    ) { buttonType, any, itemPosition ->
                        itemClickHandler.onItemClick(
                            ItemType.RECOMMENDATION,
                            -1,
                            buttonType,
                            any,
                            itemPosition
                        )
                    }
                recyclerView.adapter = recommendationAdapter
            }

            SectionType.TRACK -> {
                val trackInfoDTOList = section.items.filterIsInstance<TrackInfoDTO>()
                val trackAdapter =
                    TrackAdapter(
                        requireContext(), trackInfoDTOList
                    ) { buttonType, any, itemPosition ->
                        itemClickHandler.onItemClick(
                            ItemType.TRACK,
                            -1,
                            buttonType,
                            any,
                            itemPosition
                        )
                    }
                recyclerView.adapter = trackAdapter
            }

            else -> {
                //
            }
        }
        swipeRefreshLayout.isRefreshing = false
    }

    private fun loadData() {
        when (section.title) {
            "Мои Рекомендации" -> {
                loadRecommendations()
            }

            "Артисты" -> {
                loadArtists()
            }

            "Мои Артисты" -> {
                loadMyArtists()
            }

            "Найденные Артисты" -> {
                loadSearch(section.info)
            }

            "Альбомы" -> {
                loadAlbums()
            }

            "Мои Альбомы" -> {
                loadMyAlbums()
            }

            "Найденные Альбомы" -> {
                loadSearch(section.info)
            }

            "Мои Плейлисты" -> {
                loadMyPlaylists()
            }

            "Найденные Плейлисты" -> {
                loadSearch(section.info)
            }

            "Треки" -> {
                loadTracks()
            }

            "Мои Треки" -> {
                loadMyTracks()
            }

            "Найденные Треки" -> {
                loadSearch(section.info)
            }

            "История Прослушивания" -> {
                loadTrackHistory()
            }
        }
    }

    private fun loadRecommendations() {
        val retrofitClient = RetrofitClient()
        val recommendationAPI =
            retrofitClient.getRetrofit(requireContext()).create(RecommendationAPI::class.java)

        recommendationAPI.getUserRecommendationList()
            .enqueue(object : Callback<List<RecommendationInfoDTO>> {
                override fun onResponse(
                    call: Call<List<RecommendationInfoDTO>>,
                    response: Response<List<RecommendationInfoDTO>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        section.items = (response.body() as List<Nothing>?)!!
                        populateListView()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to load recommendations (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<RecommendationInfoDTO>>, t: Throwable) {
                    Log.e("LoadRecommendations", "Failed to load recommendations", t)
                    Toast.makeText(
                        requireContext(),
                        "Failed to load recommendations (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
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

        artistAPI.getArtistUserList().enqueue(object : Callback<List<ArtistInfoDTO>> {
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

        albumAPI.getAlbumUserList().enqueue(object : Callback<List<AlbumInfoDTO>> {
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

        playlistAPI.getPlaylistUserList().enqueue(object : Callback<List<PlaylistInfoDTO>> {
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

        trackAPI.getTrackUserList().enqueue(object : Callback<List<TrackInfoDTO>> {
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

    private fun loadTrackHistory() {
        val retrofitClient = RetrofitClient()
        val trackAPI = retrofitClient.getRetrofit(requireContext()).create(TrackAPI::class.java)

        trackAPI.getTrackHistoryList().enqueue(object : Callback<List<TrackInfoDTO>> {
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
                        "Failed to load history (onResponse)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<TrackInfoDTO>>, t: Throwable) {
                Log.e("LoadHistory", "Failed to load history", t)
                Toast.makeText(
                    requireContext(),
                    "Failed to load history (onFailure)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun loadSearch(name: String) {
        val retrofitClient = RetrofitClient()
        val searchAPI = retrofitClient.getRetrofit(requireContext()).create(SearchAPI::class.java)

        searchAPI.searchByName(SearchRequestDTO(name)).enqueue(object : Callback<SearchInfoDTO> {
            override fun onResponse(call: Call<SearchInfoDTO>, response: Response<SearchInfoDTO>) {
                if (response.isSuccessful && response.body() != null) {
                    val searchInfoDTO = response.body()!!

                    when (section.type) {
                        SectionType.ALBUM -> {
                            section.items = (searchInfoDTO.foundedAlbum as List<Nothing>)
                        }

                        SectionType.ARTIST -> {
                            section.items = (searchInfoDTO.foundedArtist as List<Nothing>)
                        }

                        SectionType.PLAYLIST -> {
                            section.items = (searchInfoDTO.foundedPlaylist as List<Nothing>)
                        }

                        SectionType.TRACK -> {
                            section.items = (searchInfoDTO.foundedTrack as List<Nothing>)
                        }

                        else -> {
                            //
                        }
                    }

                    populateListView()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Failed to load search (onResponse)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<SearchInfoDTO>, t: Throwable) {
                Log.e("LoadSearch", "Failed to load search", t)
                Toast.makeText(
                    requireContext(),
                    "Failed to load search (onFailure)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}