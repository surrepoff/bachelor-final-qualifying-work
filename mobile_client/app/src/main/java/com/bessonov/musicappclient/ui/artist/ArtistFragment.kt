package com.bessonov.musicappclient.ui.artist

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
import com.bessonov.musicappclient.api.ArtistAPI
import com.bessonov.musicappclient.api.RetrofitClient
import com.bessonov.musicappclient.api.TrackAPI
import com.bessonov.musicappclient.dto.ArtistInfoDTO
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

class ArtistFragment(
    private var artistInfoDTO: ArtistInfoDTO
) : Fragment() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var closeButton: ImageButton
    private lateinit var artisImage: ImageView
    private lateinit var artistName: TextView
    private lateinit var addButton: ImageButton
    private lateinit var likeButton: ImageButton
    private lateinit var dislikeButton: ImageButton
    private lateinit var trackRecyclerView: RecyclerView

    private lateinit var trackInfoDTOList: List<TrackInfoDTO>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_artist, container, false)

        swipeRefreshLayout = view.findViewById(R.id.fragmentArtist_swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }

        closeButton = view.findViewById(R.id.fragmentArtist_closeButton)
        closeButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        artisImage = view.findViewById(R.id.fragmentArtist_artistImage)
        loadArtistImage()

        artistName = view.findViewById(R.id.fragmentArtist_artistName)

        addButton = view.findViewById(R.id.fragmentArtist_addButton)
        addButton.setOnClickListener {
            if (artistInfoDTO.isAdded.isAdded) {
                removeArtist()
            } else {
                addArtist()
            }
        }

        likeButton = view.findViewById(R.id.fragmentArtist_likeButton)
        likeButton.setOnClickListener {
            when (artistInfoDTO.rating.name) {
                "Like" -> {
                    rateArtist(0)
                }

                else -> {
                    rateArtist(1)
                }
            }
        }

        dislikeButton = view.findViewById(R.id.fragmentArtist_dislikeButton)
        dislikeButton.setOnClickListener {
            when (artistInfoDTO.rating.name) {
                "Dislike" -> {
                    rateArtist(0)
                }

                else -> {
                    rateArtist(-1)
                }
            }
        }

        trackRecyclerView = view.findViewById(R.id.fragmentArtist_trackRecyclerView)
        trackRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadData()

        return view
    }

    private fun populateData() {
        swipeRefreshLayout.isRefreshing = false

        artistName.text = artistInfoDTO.artist.name

        if (artistInfoDTO.isAdded.isAdded) {
            addButton.setImageResource(R.drawable.ic_check)
        } else {
            addButton.setImageResource(R.drawable.ic_plus)
        }

        when (artistInfoDTO.rating.name) {
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
        loadArtist()
    }

    private fun loadArtistImage() {
        val configManager = ConfigManager(requireContext())
        val sessionManager = SessionManager(requireContext())

        val glideUrl = GlideUrl(
            configManager.getServerIp() + "/image/artist/" + artistInfoDTO.artist.id,
            LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer " + sessionManager.fetchAuthToken())
                .build()
        )

        view?.let {
            Glide.with(it)
                .load(glideUrl)
                .placeholder(R.drawable.default_artist)
                .into(artisImage)
        }
    }

    private fun loadArtist() {
        val retrofitClient = RetrofitClient()
        val artistAPI =
            retrofitClient.getRetrofit(requireContext()).create(ArtistAPI::class.java)

        artistAPI.getByArtistId(artistInfoDTO.artist.id)
            .enqueue(object : Callback<ArtistInfoDTO> {
                override fun onResponse(
                    call: Call<ArtistInfoDTO>,
                    response: Response<ArtistInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        artistInfoDTO = response.body()!!
                        loadArtistImage()
                        loadTracks()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Не удалось загрузить артиста (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ArtistInfoDTO>, t: Throwable) {
                    Log.e("LoadArtist", "Не удалось загрузить артиста", t)
                    Toast.makeText(
                        requireContext(),
                        "Не удалось загрузить артиста (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun loadTracks() {
        val retrofitClient = RetrofitClient()
        val trackAPI = retrofitClient.getRetrofit(requireContext()).create(TrackAPI::class.java)

        trackAPI.getByTrackIdList(artistInfoDTO.trackId)
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
                            "Не удалось загрузить треки (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<TrackInfoDTO>>, t: Throwable) {
                    Log.e("LoadTrack", "Не удалось загрузить треки", t)
                    Toast.makeText(
                        requireContext(),
                        "Не удалось загрузить треки (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun removeArtist() {
        val retrofitClient = RetrofitClient()
        val artistAPI =
            retrofitClient.getRetrofit(requireContext()).create(ArtistAPI::class.java)

        artistAPI.removeArtistFromUserList(artistInfoDTO.artist.id)
            .enqueue(object : Callback<ArtistInfoDTO> {
                override fun onResponse(
                    call: Call<ArtistInfoDTO>,
                    response: Response<ArtistInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        artistInfoDTO = response.body()!!
                        populateData()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Не удалось убрать артиста (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ArtistInfoDTO>, t: Throwable) {
                    Log.e("RemoveArtist", "Не удалось убрать артиста", t)
                    Toast.makeText(
                        requireContext(),
                        "Не удалось убрать артиста (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun addArtist() {
        val retrofitClient = RetrofitClient()
        val artistAPI =
            retrofitClient.getRetrofit(requireContext()).create(ArtistAPI::class.java)

        artistAPI.addArtistToUserList(artistInfoDTO.artist.id)
            .enqueue(object : Callback<ArtistInfoDTO> {
                override fun onResponse(
                    call: Call<ArtistInfoDTO>,
                    response: Response<ArtistInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        artistInfoDTO = response.body()!!
                        populateData()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Не удалось добавить артиста (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ArtistInfoDTO>, t: Throwable) {
                    Log.e("AddArtist", "Не удалось добавить артиста", t)
                    Toast.makeText(
                        requireContext(),
                        "Не удалось добавить артиста (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun rateArtist(rateId: Int) {
        val retrofitClient = RetrofitClient()
        val artistAPI =
            retrofitClient.getRetrofit(requireContext()).create(ArtistAPI::class.java)

        artistAPI.rateArtist(artistInfoDTO.artist.id, rateId)
            .enqueue(object : Callback<ArtistInfoDTO> {
                override fun onResponse(
                    call: Call<ArtistInfoDTO>,
                    response: Response<ArtistInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        artistInfoDTO = response.body()!!
                        populateData()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Не удалось оценить артиста (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ArtistInfoDTO>, t: Throwable) {
                    Log.e("RateArtist", "Не удалось оценить артиста", t)
                    Toast.makeText(
                        requireContext(),
                        "Не удалось оценить артиста (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}