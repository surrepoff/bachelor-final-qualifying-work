package com.bessonov.musicappclient.ui.playlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.adapter.trackRemove.TrackRemoveAdapter
import com.bessonov.musicappclient.api.PlaylistAPI
import com.bessonov.musicappclient.api.TrackAPI
import com.bessonov.musicappclient.dto.PlaylistEditDTO
import com.bessonov.musicappclient.dto.PlaylistInfoDTO
import com.bessonov.musicappclient.dto.TrackInfoDTO
import com.bessonov.musicappclient.ui.main.MainActivity
import com.bessonov.musicappclient.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaylistEditFragment(
    private val create: Boolean,
    private val playlistInfoDTO: PlaylistInfoDTO = PlaylistInfoDTO()
) : Fragment() {
    private val playlistEditViewModel: PlaylistEditViewModel by activityViewModels()

    private lateinit var closeButton: ImageButton
    private lateinit var headerName: TextView
    private lateinit var nameText: EditText
    private lateinit var removeButton: Button
    private lateinit var addButton: Button
    private lateinit var trackRecyclerView: RecyclerView
    private lateinit var editButton: Button
    private lateinit var cancelButton: Button

    private lateinit var trackRemoveAdapter: TrackRemoveAdapter
    private var removeMode: Boolean = false

    private var isCurrentTrackLoaded: Boolean = false
    private var isAddTrackLoaded: Boolean = false

    private lateinit var currentTrackInfoDTOList: List<TrackInfoDTO>
    private lateinit var addTrackInfoDTOList: List<TrackInfoDTO>

    private lateinit var currentTrackIdList: List<Int>
    private lateinit var removeTrackIdList: List<Int>
    private lateinit var addTrackIdList: List<Int>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist_edit, container, false)

        currentTrackInfoDTOList = emptyList()
        addTrackInfoDTOList = emptyList()

        currentTrackIdList = if (create)
            emptyList()
        else
            playlistInfoDTO.trackId
        removeTrackIdList = emptyList()
        addTrackIdList = emptyList()

        trackRecyclerView = view.findViewById(R.id.fragmentPlaylistEdit_trackRecyclerView)
        trackRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadData()

        closeButton = view.findViewById(R.id.fragmentPlaylistEdit_closeButton)
        closeButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        headerName = view.findViewById(R.id.fragmentPlaylistEdit_headerName)
        if (create)
            headerName.text = "Создать плейлист"
        else
            headerName.text = "Изменить плейлист"

        nameText = view.findViewById(R.id.fragmentPlaylistEdit_nameText)
        if (!create)
            nameText.setText(playlistInfoDTO.playlist.name)

        removeButton = view.findViewById(R.id.fragmentPlaylistEdit_trackRemoveButton)
        removeButton.setOnClickListener {
            removeMode = !removeMode
            trackRemoveAdapter.changeMode(removeMode)
        }

        addButton = view.findViewById(R.id.fragmentPlaylistEdit_trackAddButton)
        addButton.setOnClickListener {
            playlistEditViewModel.setCurrentTrackIdList(currentTrackIdList)
            playlistEditViewModel.setAddTrackIdList(addTrackIdList)
            playlistEditViewModel.setRemoveTrackIdList(removeTrackIdList)
            (activity as MainActivity).openPlaylistAddTrackFragment()
        }

        editButton = view.findViewById(R.id.fragmentPlaylistEdit_editButton)
        if (create)
            editButton.text = "Создать"
        else
            editButton.text = "Изменить"
        editButton.setOnClickListener {
            val name = nameText.text.toString()

            var trackId: List<Int> = currentTrackIdList.filter { it !in removeTrackIdList }
            trackId = trackId + addTrackIdList

            val playlistEditDTO = PlaylistEditDTO(name, trackId)

            if (create) {
                createPlaylist(playlistEditDTO)
            } else {
                editPlaylist(playlistEditDTO)
            }

            playlistEditViewModel.setCurrentTrackIdList(emptyList())
            playlistEditViewModel.setAddTrackIdList(emptyList())
            playlistEditViewModel.setRemoveTrackIdList(emptyList())
        }

        cancelButton = view.findViewById(R.id.fragmentPlaylistEdit_cancelButton)
        cancelButton.setOnClickListener {
            playlistEditViewModel.setCurrentTrackIdList(emptyList())
            playlistEditViewModel.setAddTrackIdList(emptyList())
            playlistEditViewModel.setRemoveTrackIdList(emptyList())
            activity?.supportFragmentManager?.popBackStack()
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        if (playlistEditViewModel.addTrackIdList.value != null)
            addTrackIdList = playlistEditViewModel.addTrackIdList.value!!
        if (playlistEditViewModel.removeTrackIdList.value != null)
            removeTrackIdList = playlistEditViewModel.removeTrackIdList.value!!

        loadData()
    }

    private fun checkIsInfoLoaded() {
        if ((isCurrentTrackLoaded || currentTrackIdList.isEmpty()) && (isAddTrackLoaded || addTrackIdList.isEmpty())) {
            populateData()
            isCurrentTrackLoaded = false
            isAddTrackLoaded = false
        }
    }

    private fun populateData() {
        trackRemoveAdapter = TrackRemoveAdapter(
            requireContext(),
            currentTrackInfoDTOList + addTrackInfoDTOList
        ) { trackId, position ->
            if (removeTrackIdList.contains(trackId)) {
                removeTrackIdList = removeTrackIdList.filter { it != trackId }
            } else {
                if (addTrackIdList.contains(trackId)) {
                    addTrackIdList = addTrackIdList.filter { it != trackId }
                } else {
                    removeTrackIdList = removeTrackIdList + trackId
                }
            }
            trackRecyclerView.adapter?.notifyItemChanged(position)
        }
        trackRecyclerView.adapter = trackRemoveAdapter
        trackRemoveAdapter.changeMode(removeMode)
    }

    private fun loadData() {
        if (currentTrackIdList.isNotEmpty())
            loadCurrentTracks()
        if (addTrackIdList.isNotEmpty())
            loadAddTracks()
        if (currentTrackIdList.isEmpty() && addTrackIdList.isEmpty())
            populateData()
    }

    private fun loadCurrentTracks() {
        val retrofitClient = RetrofitClient()
        val trackAPI = retrofitClient.getRetrofit(requireContext()).create(TrackAPI::class.java)

        trackAPI.getByTrackIdList(currentTrackIdList)
            .enqueue(object : Callback<List<TrackInfoDTO>> {
                override fun onResponse(
                    call: Call<List<TrackInfoDTO>>,
                    response: Response<List<TrackInfoDTO>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        currentTrackInfoDTOList = response.body()!!
                        isCurrentTrackLoaded = true
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
                    Log.e("LoadTrack", "Failed to load tracks", t)
                    Toast.makeText(
                        requireContext(),
                        "Failed to load tracks (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun loadAddTracks() {
        val retrofitClient = RetrofitClient()
        val trackAPI = retrofitClient.getRetrofit(requireContext()).create(TrackAPI::class.java)

        trackAPI.getByTrackIdList(addTrackIdList).enqueue(object : Callback<List<TrackInfoDTO>> {
            override fun onResponse(
                call: Call<List<TrackInfoDTO>>,
                response: Response<List<TrackInfoDTO>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    addTrackInfoDTOList = response.body()!!
                    isAddTrackLoaded = true
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
                Log.e("LoadTrack", "Failed to load tracks", t)
                Toast.makeText(
                    requireContext(),
                    "Failed to load tracks (onFailure)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun createPlaylist(playlistEditDTO: PlaylistEditDTO) {
        val retrofitClient = RetrofitClient()
        val playlistAPI =
            retrofitClient.getRetrofit(requireContext()).create(PlaylistAPI::class.java)

        playlistAPI.createPlaylist(playlistEditDTO).enqueue(object : Callback<PlaylistInfoDTO> {
            override fun onResponse(
                call: Call<PlaylistInfoDTO>,
                response: Response<PlaylistInfoDTO>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val playlist = response.body()!!
                    activity?.supportFragmentManager?.popBackStack()
                    (activity as MainActivity).openPlaylistFragment(playlist)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Failed to create playlist (onResponse)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<PlaylistInfoDTO>, t: Throwable) {
                Log.e("CreatePlaylist", "Failed to create playlist", t)
                Toast.makeText(
                    requireContext(),
                    "Failed to create playlist (onFailure)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun editPlaylist(playlistEditDTO: PlaylistEditDTO) {
        val retrofitClient = RetrofitClient()
        val playlistAPI =
            retrofitClient.getRetrofit(requireContext()).create(PlaylistAPI::class.java)

        playlistAPI.editPlaylist(playlistInfoDTO.playlist.id, playlistEditDTO)
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
                            "Failed to edit playlist (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<PlaylistInfoDTO>, t: Throwable) {
                    Log.e("EditPlaylist", "Failed to edit playlist", t)
                    Toast.makeText(
                        requireContext(),
                        "Failed to edit playlist (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}