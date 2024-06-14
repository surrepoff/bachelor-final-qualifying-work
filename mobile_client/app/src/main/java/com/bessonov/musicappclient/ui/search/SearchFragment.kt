package com.bessonov.musicappclient.ui.search

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
import com.bessonov.musicappclient.api.RetrofitClient
import com.bessonov.musicappclient.api.UserAPI
import com.bessonov.musicappclient.dto.AlbumInfoDTO
import com.bessonov.musicappclient.dto.ArtistInfoDTO
import com.bessonov.musicappclient.dto.PlaylistInfoDTO
import com.bessonov.musicappclient.dto.TrackInfoDTO
import com.bessonov.musicappclient.dto.UserDataDTO
import com.bessonov.musicappclient.ui.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var userImage: ImageButton
    private lateinit var userNickname: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var sectionList: List<Section<*>>

    private lateinit var artistInfoDTOList: List<ArtistInfoDTO>
    private lateinit var albumInfoDTOList: List<AlbumInfoDTO>
    private lateinit var playlistInfoDTOList: List<PlaylistInfoDTO>
    private lateinit var trackInfoDTOList: List<TrackInfoDTO>
    private lateinit var userDataDTO: UserDataDTO

    private var isArtistInfoLoaded: Boolean = false
    private var isAlbumInfoLoaded: Boolean = false
    private var isPlaylistInfoLoaded: Boolean = false
    private var isTrackInfoLoaded: Boolean = false
    private var isUserInfoLoaded: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        swipeRefreshLayout = view.findViewById(R.id.fragmentSearch_swipeRefreshLayout)

        userImage = view.findViewById(R.id.fragmentSearch_userImage)
        userNickname = view.findViewById(R.id.fragmentSearch_userNickname)

        recyclerView = view.findViewById(R.id.fragmentSearch_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                swipeRefreshLayout.isEnabled = !recyclerView.canScrollVertically(-1)
            }
        })

        artistInfoDTOList = emptyList()
        albumInfoDTOList = emptyList()
        playlistInfoDTOList = emptyList()
        trackInfoDTOList = emptyList()
        userDataDTO = UserDataDTO()

        isArtistInfoLoaded = false
        isAlbumInfoLoaded = false
        isPlaylistInfoLoaded = false
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
        if (isUserInfoLoaded) {
            populateData()
            isUserInfoLoaded = false
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun populateData() {
        userNickname.text = userDataDTO.nickname
    }

    private fun loadData() {
        loadUser()
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