package com.bessonov.musicappclient.ui.home

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
import com.bessonov.musicappclient.adapter.section.Section
import com.bessonov.musicappclient.adapter.section.SectionAdapter
import com.bessonov.musicappclient.adapter.section.SectionType
import com.bessonov.musicappclient.api.AlbumAPI
import com.bessonov.musicappclient.api.ArtistAPI
import com.bessonov.musicappclient.api.RetrofitClient
import com.bessonov.musicappclient.api.TrackAPI
import com.bessonov.musicappclient.api.UserAPI
import com.bessonov.musicappclient.dto.AlbumInfoDTO
import com.bessonov.musicappclient.dto.ArtistInfoDTO
import com.bessonov.musicappclient.dto.TrackInfoDTO
import com.bessonov.musicappclient.dto.UserDataDTO
import com.bessonov.musicappclient.ui.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var userImage: ImageButton
    private lateinit var userNickname: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var sectionList: List<Section<*>>

    private lateinit var artistInfoDTOList: List<ArtistInfoDTO>
    private lateinit var albumInfoDTOList: List<AlbumInfoDTO>
    private lateinit var trackInfoDTOList: List<TrackInfoDTO>
    private lateinit var userDataDTO: UserDataDTO

    private var isArtistInfoLoaded: Boolean = false
    private var isAlbumInfoLoaded: Boolean = false
    private var isTrackInfoLoaded: Boolean = false
    private var isUserInfoLoaded: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        swipeRefreshLayout = view.findViewById(R.id.fragmentHome_swipeRefreshLayout)

        userImage = view.findViewById(R.id.fragmentHome_userImage)
        userNickname = view.findViewById(R.id.fragmentHome_userNickname)

        recyclerView = view.findViewById(R.id.fragmentHome_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                swipeRefreshLayout.isEnabled = !recyclerView.canScrollVertically(-1)
            }
        })

        artistInfoDTOList = emptyList()
        albumInfoDTOList = emptyList()
        trackInfoDTOList = emptyList()
        userDataDTO = UserDataDTO()

        isArtistInfoLoaded = false
        isAlbumInfoLoaded = false
        isTrackInfoLoaded = false
        isUserInfoLoaded = false

        loadData()

        swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }

        userImage.setOnClickListener {
            (activity as MainActivity).openProfileFragment()
        }

        userNickname.setOnClickListener {
            (activity as MainActivity).openProfileFragment()
        }

        return view
    }

    private fun checkIsInfoLoaded() {
        if (isArtistInfoLoaded && isAlbumInfoLoaded && isTrackInfoLoaded && isUserInfoLoaded) {
            populateData()
            isArtistInfoLoaded = false
            isAlbumInfoLoaded = false
            isTrackInfoLoaded = false
            isUserInfoLoaded = false
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun populateData() {
        userNickname.text = userDataDTO.nickname

        val artistSection: Section<ArtistInfoDTO> = Section(
            title = "Artists",
            type = SectionType.ARTIST,
            items = artistInfoDTOList,
            orientation = LinearLayoutManager.HORIZONTAL
        )

        val albumSection: Section<AlbumInfoDTO> = Section(
            title = "Albums",
            type = SectionType.ALBUM,
            items = albumInfoDTOList,
            orientation = LinearLayoutManager.HORIZONTAL
        )

        val trackSection: Section<TrackInfoDTO> = Section(
            title = "Tracks",
            type = SectionType.TRACK,
            items = trackInfoDTOList,
            orientation = LinearLayoutManager.VERTICAL
        )

        sectionList = listOf(artistSection, albumSection, trackSection)

        val sectionAdapter = SectionAdapter(requireContext(), sectionList) { section ->
            (activity as MainActivity).openSectionFragment(section)
        }
        recyclerView.adapter = sectionAdapter
    }

    private fun loadData() {
        loadArtists()
        loadAlbums()
        loadTracks()
        loadUser()
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
                    artistInfoDTOList = response.body()!!
                    isArtistInfoLoaded = true
                    checkIsInfoLoaded()
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
                    albumInfoDTOList = response.body()!!
                    isAlbumInfoLoaded = true
                    checkIsInfoLoaded()
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

    private fun loadTracks() {
        val retrofitClient = RetrofitClient()
        val trackAPI = retrofitClient.getRetrofit(requireContext()).create(TrackAPI::class.java)

        trackAPI.getAll().enqueue(object : Callback<List<TrackInfoDTO>> {
            override fun onResponse(
                call: Call<List<TrackInfoDTO>>,
                response: Response<List<TrackInfoDTO>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    trackInfoDTOList = response.body()!!
                    isTrackInfoLoaded = true
                    checkIsInfoLoaded()
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

    private fun loadUser() {
        val retrofitClient = RetrofitClient()
        val userAPI = retrofitClient.getRetrofit(requireContext()).create(UserAPI::class.java)

        userAPI.info().enqueue(object : Callback<UserDataDTO> {
            override fun onResponse(call: Call<UserDataDTO>, response: Response<UserDataDTO>) {
                if (response.isSuccessful && response.body() != null) {
                    userDataDTO = response.body()!!
                    isUserInfoLoaded = true
                    checkIsInfoLoaded()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Failed to load user (onResponse)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<UserDataDTO>, t: Throwable) {
                Log.e("LoadUser", "Failed to load user", t)
                Toast.makeText(
                    requireContext(),
                    "Failed to load user (onFailure)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}