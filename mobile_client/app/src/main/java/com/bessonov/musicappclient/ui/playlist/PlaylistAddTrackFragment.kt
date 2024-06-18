package com.bessonov.musicappclient.ui.playlist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.adapter.trackAdd.TrackAddAdapter
import com.bessonov.musicappclient.api.SearchAPI
import com.bessonov.musicappclient.api.TrackAPI
import com.bessonov.musicappclient.dto.SearchRequestDTO
import com.bessonov.musicappclient.dto.TrackInfoDTO
import com.bessonov.musicappclient.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaylistAddTrackFragment : Fragment() {
    private val playlistEditViewModel: PlaylistEditViewModel by activityViewModels()

    private lateinit var closeButton: ImageButton
    private lateinit var searchText: EditText
    private lateinit var trackRecyclerView: RecyclerView
    private lateinit var addButton: Button
    private lateinit var cancelButton: Button

    private lateinit var currentTrackIdList: List<Int>
    private lateinit var addTrackIdList: List<Int>
    private lateinit var removeTrackIdList: List<Int>

    private lateinit var searchTrackInfoDTO: List<TrackInfoDTO>
    private lateinit var userTrackInfoDTOList: List<TrackInfoDTO>

    private var isSearchTrackInfoLoaded: Boolean = false
    private var isUserTrackInfoLoaded: Boolean = false

    private var isSearch: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist_add_track, container, false)

        currentTrackIdList = emptyList()
        addTrackIdList = emptyList()
        removeTrackIdList = emptyList()

        currentTrackIdList = playlistEditViewModel.currentTrackIdList.value!!
        addTrackIdList = playlistEditViewModel.addTrackIdList.value!!
        removeTrackIdList = playlistEditViewModel.removeTrackIdList.value!!

        closeButton = view.findViewById(R.id.fragmentPlaylistAddTrack_closeButton)
        closeButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        searchText = view.findViewById(R.id.fragmentPlaylistAddTrack_searchText)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                checkSearchField()
            }
        }

        searchText.addTextChangedListener(textWatcher)

        trackRecyclerView = view.findViewById(R.id.fragmentPlaylistAddTrack_trackRecyclerView)
        trackRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadData()

        addButton = view.findViewById(R.id.fragmentPlaylistAddTrack_addButton)
        addButton.setOnClickListener {
            playlistEditViewModel.setAddTrackIdList(addTrackIdList)
            playlistEditViewModel.setRemoveTrackIdList(removeTrackIdList)
            activity?.supportFragmentManager?.popBackStack()
        }

        cancelButton = view.findViewById(R.id.fragmentPlaylistAddTrack_cancelButton)
        cancelButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        return view
    }

    private fun checkSearchField() {
        isSearch = searchText.text.toString().isNotBlank()
        loadData()
    }

    private fun checkIsInfoLoaded() {
        if (isSearch) {
            if (isSearchTrackInfoLoaded) {
                populateData()
                isSearchTrackInfoLoaded = false
            }
        } else {
            if (isUserTrackInfoLoaded) {
                populateData()
                isUserTrackInfoLoaded = false
            }
        }
    }

    private fun populateData() {
        if (isSearch) {
            val trackAddAdapter = TrackAddAdapter(
                requireContext(),
                searchTrackInfoDTO,
                currentTrackIdList,
                addTrackIdList,
                removeTrackIdList
            ) { trackId, position ->
                if (addTrackIdList.contains(trackId)) {
                    addTrackIdList = addTrackIdList.filter { it != trackId }
                } else {
                    if (removeTrackIdList.contains(trackId)) {
                        removeTrackIdList = removeTrackIdList.filter { it != trackId }
                    } else {
                        if (currentTrackIdList.contains(trackId)) {
                            removeTrackIdList = removeTrackIdList + trackId
                        } else {
                            addTrackIdList = addTrackIdList + trackId
                        }
                    }
                }
                trackRecyclerView.adapter?.notifyItemChanged(position)
            }
            trackRecyclerView.adapter = trackAddAdapter
        } else {
            val trackAddAdapter = TrackAddAdapter(
                requireContext(),
                userTrackInfoDTOList,
                currentTrackIdList,
                addTrackIdList,
                removeTrackIdList
            ) { trackId, position ->
                if (addTrackIdList.contains(trackId)) {
                    addTrackIdList = addTrackIdList.filter { it != trackId }
                } else {
                    if (removeTrackIdList.contains(trackId)) {
                        removeTrackIdList = removeTrackIdList.filter { it != trackId }
                    } else {
                        if (currentTrackIdList.contains(trackId)) {
                            removeTrackIdList = removeTrackIdList + trackId
                        } else {
                            addTrackIdList = addTrackIdList + trackId
                        }
                    }
                }
                trackRecyclerView.adapter?.notifyItemChanged(position)
            }
            trackRecyclerView.adapter = trackAddAdapter
        }
    }

    private fun loadData() {
        if (isSearch) {
            loadSearchTracks()
        } else {
            loadUserTracks()
        }
    }

    private fun loadSearchTracks() {
        val retrofitClient = RetrofitClient()
        val searchAPI = retrofitClient.getRetrofit(requireContext()).create(SearchAPI::class.java)

        searchAPI.searchTrackByName(SearchRequestDTO(searchText.text.toString()))
            .enqueue(object : Callback<List<TrackInfoDTO>> {
                override fun onResponse(
                    call: Call<List<TrackInfoDTO>>,
                    response: Response<List<TrackInfoDTO>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        searchTrackInfoDTO = response.body()!!
                        isSearchTrackInfoLoaded = true
                        checkIsInfoLoaded()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to load search (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<TrackInfoDTO>>, t: Throwable) {
                    Log.e("LoadSearch", "Failed to load search", t)
                    Toast.makeText(
                        requireContext(),
                        "Failed to load search (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun loadUserTracks() {
        val retrofitClient = RetrofitClient()
        val trackAPI = retrofitClient.getRetrofit(requireContext()).create(TrackAPI::class.java)

        trackAPI.getTrackUserList().enqueue(object : Callback<List<TrackInfoDTO>> {
            override fun onResponse(
                call: Call<List<TrackInfoDTO>>,
                response: Response<List<TrackInfoDTO>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    userTrackInfoDTOList = response.body()!!
                    isUserTrackInfoLoaded = true
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
}