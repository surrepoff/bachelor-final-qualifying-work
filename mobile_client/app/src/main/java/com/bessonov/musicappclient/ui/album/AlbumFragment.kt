package com.bessonov.musicappclient.ui.album

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.adapter.track.TrackAdapter
import com.bessonov.musicappclient.api.AlbumAPI
import com.bessonov.musicappclient.api.RetrofitClient
import com.bessonov.musicappclient.api.TrackAPI
import com.bessonov.musicappclient.dto.AlbumInfoDTO
import com.bessonov.musicappclient.dto.TrackInfoDTO
import com.bessonov.musicappclient.manager.ConfigManager
import com.bessonov.musicappclient.manager.SessionManager
import com.bessonov.musicappclient.ui.main.MainActivity
import com.bessonov.musicappclient.utils.ItemClickHandler
import com.bessonov.musicappclient.utils.ItemType
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class AlbumFragment(
    private var albumInfoDTO: AlbumInfoDTO
) : Fragment() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var closeButton: ImageButton
    private lateinit var albumImage: ImageView
    private lateinit var albumName: TextView
    private lateinit var artistName: TextView
    private lateinit var addButton: ImageButton
    private lateinit var likeButton: ImageButton
    private lateinit var dislikeButton: ImageButton
    private lateinit var releaseText: TextView
    private lateinit var trackRecyclerView: RecyclerView

    private lateinit var trackInfoDTOList: List<TrackInfoDTO>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_album, container, false)

        swipeRefreshLayout = view.findViewById(R.id.fragmentAlbum_swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }

        closeButton = view.findViewById(R.id.fragmentAlbum_closeButton)
        closeButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        albumImage = view.findViewById(R.id.fragmentAlbum_albumImage)
        loadAlbumImage()

        albumName = view.findViewById(R.id.fragmentAlbum_albumName)
        artistName = view.findViewById(R.id.fragmentAlbum_artistName)

        addButton = view.findViewById(R.id.fragmentAlbum_addButton)
        addButton.setOnClickListener {
            if (albumInfoDTO.isAdded.isAdded) {
                removeAlbum()
            } else {
                addAlbum()
            }
        }

        likeButton = view.findViewById(R.id.fragmentAlbum_likeButton)
        likeButton.setOnClickListener {
            when (albumInfoDTO.rating.name) {
                "Like" -> {
                    rateAlbum(0)
                }

                else -> {
                    rateAlbum(1)
                }
            }
        }

        dislikeButton = view.findViewById(R.id.fragmentAlbum_dislikeButton)
        dislikeButton.setOnClickListener {
            when (albumInfoDTO.rating.name) {
                "Dislike" -> {
                    rateAlbum(0)
                }

                else -> {
                    rateAlbum(-1)
                }
            }
        }

        releaseText = view.findViewById(R.id.fragmentAlbum_releaseText)

        trackRecyclerView = view.findViewById(R.id.fragmentAlbum_trackRecyclerView)
        trackRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadData()

        return view
    }

    private fun populateData() {
        swipeRefreshLayout.isRefreshing = false

        albumName.text = albumInfoDTO.album.name

        var name = ""
        name += albumInfoDTO.artist.joinToString(", ") { it.name }
        if (albumInfoDTO.featuredArtist.size != 0) {
            name += " feat. " + albumInfoDTO.featuredArtist.joinToString(", ") { it.name }
        }
        artistName.text = name

        if (albumInfoDTO.isAdded.isAdded) {
            addButton.setImageResource(R.drawable.ic_check)
        } else {
            addButton.setImageResource(R.drawable.ic_plus)
        }

        when (albumInfoDTO.rating.name) {
            "Like" -> {
                likeButton.setImageResource(R.drawable.ic_thumb_up)
                dislikeButton.setImageResource(R.drawable.ic_thumb_down_outline)
            }

            "Dislike" -> {
                likeButton.setImageResource(R.drawable.ic_thumb_up_outline)
                dislikeButton.setImageResource(R.drawable.ic_thumb_down)
            }

            else -> {
                likeButton.setImageResource(R.drawable.ic_thumb_up_outline)
                dislikeButton.setImageResource(R.drawable.ic_thumb_down_outline)
            }
        }

        val format = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
        releaseText.text = format.format(albumInfoDTO.album.releaseDate)

        val itemClickHandler =
            ItemClickHandler(activity as MainActivity, requireContext(), trackRecyclerView)

        val trackAdapter = TrackAdapter(
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

        trackRecyclerView.adapter = trackAdapter
    }

    private fun loadData() {
        loadAlbum()
    }

    private fun loadAlbumImage() {
        val configManager = ConfigManager(requireContext())
        val sessionManager = SessionManager(requireContext())

        val glideUrl = GlideUrl(
            configManager.getServerIp() + "/image/album/" + albumInfoDTO.album.id,
            LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer " + sessionManager.fetchAuthToken())
                .build()
        )

        view?.let {
            Glide.with(it)
                .load(glideUrl)
                .placeholder(R.drawable.default_album)
                .into(albumImage)
        }
    }

    private fun loadAlbum() {
        val retrofitClient = RetrofitClient()
        val albumAPI =
            retrofitClient.getRetrofit(requireContext()).create(AlbumAPI::class.java)

        albumAPI.getByAlbumId(albumInfoDTO.album.id)
            .enqueue(object : Callback<AlbumInfoDTO> {
                override fun onResponse(
                    call: Call<AlbumInfoDTO>,
                    response: Response<AlbumInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        albumInfoDTO = response.body()!!
                        loadAlbumImage()
                        loadTracks()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to load album (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<AlbumInfoDTO>, t: Throwable) {
                    Log.e("LoadAlbum", "Failed to load album", t)
                    Toast.makeText(
                        requireContext(),
                        "Failed to load album (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun loadTracks() {
        val retrofitClient = RetrofitClient()
        val trackAPI = retrofitClient.getRetrofit(requireContext()).create(TrackAPI::class.java)

        trackAPI.getByTrackIdList(albumInfoDTO.trackId)
            .enqueue(object : Callback<List<TrackInfoDTO>> {
                override fun onResponse(
                    call: Call<List<TrackInfoDTO>>,
                    response: Response<List<TrackInfoDTO>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        trackInfoDTOList = response.body()!!
                        populateData()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to load tracks (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<TrackInfoDTO>>, t: Throwable) {
                    Log.e("LoadTrack", "Failed to load tracks", t)
                    Toast.makeText(
                        requireContext(),
                        "Failed to load tracks (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun removeAlbum() {
        val retrofitClient = RetrofitClient()
        val albumAPI =
            retrofitClient.getRetrofit(requireContext()).create(AlbumAPI::class.java)

        albumAPI.removeAlbumFromUserList(albumInfoDTO.album.id)
            .enqueue(object : Callback<AlbumInfoDTO> {
                override fun onResponse(
                    call: Call<AlbumInfoDTO>,
                    response: Response<AlbumInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        albumInfoDTO = response.body()!!
                        populateData()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to remove album (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<AlbumInfoDTO>, t: Throwable) {
                    Log.e("RemoveAlbum", "Failed to remove album", t)
                    Toast.makeText(
                        requireContext(),
                        "Failed to remove album (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun addAlbum() {
        val retrofitClient = RetrofitClient()
        val albumAPI =
            retrofitClient.getRetrofit(requireContext()).create(AlbumAPI::class.java)

        albumAPI.addAlbumToUserList(albumInfoDTO.album.id)
            .enqueue(object : Callback<AlbumInfoDTO> {
                override fun onResponse(
                    call: Call<AlbumInfoDTO>,
                    response: Response<AlbumInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        albumInfoDTO = response.body()!!
                        populateData()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to add album (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<AlbumInfoDTO>, t: Throwable) {
                    Log.e("AddAlbum", "Failed to add album", t)
                    Toast.makeText(
                        requireContext(),
                        "Failed to add album (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun rateAlbum(rateId: Int) {
        val retrofitClient = RetrofitClient()
        val albumAPI =
            retrofitClient.getRetrofit(requireContext()).create(AlbumAPI::class.java)

        albumAPI.rateAlbum(albumInfoDTO.album.id, rateId)
            .enqueue(object : Callback<AlbumInfoDTO> {
                override fun onResponse(
                    call: Call<AlbumInfoDTO>,
                    response: Response<AlbumInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        albumInfoDTO = response.body()!!
                        populateData()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to rate album (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<AlbumInfoDTO>, t: Throwable) {
                    Log.e("RateAlbum", "Failed to rate album", t)
                    Toast.makeText(
                        requireContext(),
                        "Failed to rate album (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}