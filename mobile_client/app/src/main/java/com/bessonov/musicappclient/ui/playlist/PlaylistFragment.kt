package com.bessonov.musicappclient.ui.playlist

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
import com.bessonov.musicappclient.adapter.track.TrackAdapter
import com.bessonov.musicappclient.api.PlaylistAPI
import com.bessonov.musicappclient.api.RetrofitClient
import com.bessonov.musicappclient.api.TrackAPI
import com.bessonov.musicappclient.dto.PlaylistInfoDTO
import com.bessonov.musicappclient.dto.TrackInfoDTO
import com.bessonov.musicappclient.ui.main.MainActivity
import com.bessonov.musicappclient.utils.ItemClickHandler
import com.bessonov.musicappclient.utils.ItemType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class PlaylistFragment(
    private var playlistInfoDTO: PlaylistInfoDTO
) : Fragment() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var closeButton: ImageButton
    private lateinit var playlistEdit: ImageButton
    private lateinit var playlistName: TextView
    private lateinit var ownerName: TextView
    private lateinit var deleteButton: ImageButton
    private lateinit var addButton: ImageButton
    private lateinit var likeButton: ImageButton
    private lateinit var dislikeButton: ImageButton
    private lateinit var createText: TextView
    private lateinit var lastUpdateText: TextView
    private lateinit var trackRecyclerView: RecyclerView

    private val ownerAccessId: Int = 0
    private val moderatorAccessId: Int = 1
    private val listenerAccessId: Int = 2

    private lateinit var trackInfoDTOList: List<TrackInfoDTO>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist, container, false)

        swipeRefreshLayout = view.findViewById(R.id.fragmentPlaylist_swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }

        closeButton = view.findViewById(R.id.fragmentPlaylist_closeButton)
        closeButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        playlistEdit = view.findViewById(R.id.fragmentPlaylist_playlistEdit)
        playlistEdit.setOnClickListener {
            (activity as MainActivity).openPlaylistEditFragment(playlistInfoDTO)
        }

        playlistName = view.findViewById(R.id.fragmentPlaylist_playlistName)

        ownerName = view.findViewById(R.id.fragmentPlaylist_ownerName)

        deleteButton = view.findViewById(R.id.fragmentPlaylist_deleteButton)
        deleteButton.setOnClickListener {
            deletePlaylist()
        }

        addButton = view.findViewById(R.id.fragmentPlaylist_addButton)
        addButton.setOnClickListener {
            if (playlistInfoDTO.isAdded.isAdded) {
                removePlaylist()
            } else {
                addPlaylist()
            }
        }

        likeButton = view.findViewById(R.id.fragmentPlaylist_likeButton)
        likeButton.setOnClickListener {
            when (playlistInfoDTO.rating.name) {
                "Like" -> {
                    ratePlaylist(0)
                }

                else -> {
                    ratePlaylist(1)
                }
            }
        }

        dislikeButton = view.findViewById(R.id.fragmentPlaylist_dislikeButton)
        dislikeButton.setOnClickListener {
            when (playlistInfoDTO.rating.name) {
                "Dislike" -> {
                    ratePlaylist(0)
                }

                else -> {
                    ratePlaylist(-1)
                }
            }
        }

        createText = view.findViewById(R.id.fragmentPlaylist_createText)
        lastUpdateText = view.findViewById(R.id.fragmentPlaylist_lastUpdateText)

        trackRecyclerView = view.findViewById(R.id.fragmentPlaylist_trackRecyclerView)
        trackRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadData()

        return view
    }

    private fun populateData() {
        swipeRefreshLayout.isRefreshing = false

        if (playlistInfoDTO.accessLevel.id == ownerAccessId || playlistInfoDTO.accessLevel.id == moderatorAccessId) {
            playlistEdit.visibility = View.VISIBLE
        } else {
            playlistEdit.visibility = View.INVISIBLE
        }

        playlistName.text = playlistInfoDTO.playlist.name
        ownerName.text = playlistInfoDTO.owner.joinToString(", ") { it.nickname }

        if (playlistInfoDTO.accessLevel.id == ownerAccessId) {
            deleteButton.visibility = View.VISIBLE
        } else {
            deleteButton.visibility = View.INVISIBLE
        }

        if (playlistInfoDTO.isAdded.isAdded) {
            addButton.setImageResource(R.drawable.ic_check)
        } else {
            addButton.setImageResource(R.drawable.ic_plus)
        }

        when (playlistInfoDTO.rating.name) {
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

        val dateFormat = SimpleDateFormat("d MMMM yyyy HH:mm:ss", Locale("ru"))
        createText.text = dateFormat.format(playlistInfoDTO.playlist.creationDate)
        lastUpdateText.text = dateFormat.format(playlistInfoDTO.playlist.lastUpdateDate)

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
        loadPlaylist()
    }

    private fun loadPlaylist() {
        val retrofitClient = RetrofitClient()
        val playlistAPI =
            retrofitClient.getRetrofit(requireContext()).create(PlaylistAPI::class.java)

        playlistAPI.getByPlaylistId(playlistInfoDTO.playlist.id)
            .enqueue(object : Callback<PlaylistInfoDTO> {
                override fun onResponse(
                    call: Call<PlaylistInfoDTO>,
                    response: Response<PlaylistInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        playlistInfoDTO = response.body()!!
                        loadTracks()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Не удалось загрузить плейлист (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<PlaylistInfoDTO>, t: Throwable) {
                    Log.e("LoadPlaylist", "Не удалось загрузить плейлист", t)
                    Toast.makeText(
                        requireContext(),
                        "Не удалось загрузить плейлист (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun loadTracks() {
        val retrofitClient = RetrofitClient()
        val trackAPI = retrofitClient.getRetrofit(requireContext()).create(TrackAPI::class.java)

        trackAPI.getByTrackIdList(playlistInfoDTO.trackId)
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

    private fun deletePlaylist() {
        val retrofitClient = RetrofitClient()
        val playlistAPI =
            retrofitClient.getRetrofit(requireContext()).create(PlaylistAPI::class.java)

        playlistAPI.deletePlaylist(playlistInfoDTO.playlist.id)
            .enqueue(object : Callback<PlaylistInfoDTO> {
                override fun onResponse(
                    call: Call<PlaylistInfoDTO>,
                    response: Response<PlaylistInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        activity?.supportFragmentManager?.popBackStack()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Не удалось удалить плейлист (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<PlaylistInfoDTO>, t: Throwable) {
                    Log.e("DeletePlaylist", "Не удалось удалить плейлист", t)
                    Toast.makeText(
                        requireContext(),
                        "Не удалось удалить плейлист (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun removePlaylist() {
        val retrofitClient = RetrofitClient()
        val playlistAPI =
            retrofitClient.getRetrofit(requireContext()).create(PlaylistAPI::class.java)

        playlistAPI.removePlaylistFromUserList(playlistInfoDTO.playlist.id)
            .enqueue(object : Callback<PlaylistInfoDTO> {
                override fun onResponse(
                    call: Call<PlaylistInfoDTO>,
                    response: Response<PlaylistInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        playlistInfoDTO = response.body()!!
                        populateData()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Не удалось убрать плейлист (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<PlaylistInfoDTO>, t: Throwable) {
                    Log.e("RemovePlaylist", "Не удалось убрать плейлист", t)
                    Toast.makeText(
                        requireContext(),
                        "Не удалось убрать плейлист (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun addPlaylist() {
        val retrofitClient = RetrofitClient()
        val playlistAPI =
            retrofitClient.getRetrofit(requireContext()).create(PlaylistAPI::class.java)

        playlistAPI.addPlaylistToUserList(playlistInfoDTO.playlist.id)
            .enqueue(object : Callback<PlaylistInfoDTO> {
                override fun onResponse(
                    call: Call<PlaylistInfoDTO>,
                    response: Response<PlaylistInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        playlistInfoDTO = response.body()!!
                        populateData()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Не удалось добавить плейлист (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<PlaylistInfoDTO>, t: Throwable) {
                    Log.e("AddPlaylist", "Не удалось добавить плейлист", t)
                    Toast.makeText(
                        requireContext(),
                        "Не удалось добавить плейлист (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun ratePlaylist(rateId: Int) {
        val retrofitClient = RetrofitClient()
        val playlistAPI =
            retrofitClient.getRetrofit(requireContext()).create(PlaylistAPI::class.java)

        playlistAPI.ratePlaylist(playlistInfoDTO.playlist.id, rateId)
            .enqueue(object : Callback<PlaylistInfoDTO> {
                override fun onResponse(
                    call: Call<PlaylistInfoDTO>,
                    response: Response<PlaylistInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        playlistInfoDTO = response.body()!!
                        populateData()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Не удалось оценить плейлист (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<PlaylistInfoDTO>, t: Throwable) {
                    Log.e("RatePlaylist", "Не удалось оценить плейлист", t)
                    Toast.makeText(
                        requireContext(),
                        "Не удалось оценить плейлист (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}