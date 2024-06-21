package com.bessonov.musicappclient.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
import com.bessonov.musicappclient.api.RetrofitClient
import com.bessonov.musicappclient.api.SearchAPI
import com.bessonov.musicappclient.api.TrackAPI
import com.bessonov.musicappclient.api.UserAPI
import com.bessonov.musicappclient.dto.AlbumInfoDTO
import com.bessonov.musicappclient.dto.ArtistInfoDTO
import com.bessonov.musicappclient.dto.PlaylistInfoDTO
import com.bessonov.musicappclient.dto.SearchInfoDTO
import com.bessonov.musicappclient.dto.SearchRequestDTO
import com.bessonov.musicappclient.dto.TrackInfoDTO
import com.bessonov.musicappclient.dto.UserDataDTO
import com.bessonov.musicappclient.ui.main.MainActivity
import com.bessonov.musicappclient.utils.ItemClickHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var userImage: ImageButton
    private lateinit var userNickname: TextView
    private lateinit var searchText: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var sectionList: List<Section<*>>

    private lateinit var searchInfoDTO: SearchInfoDTO
    private lateinit var historyTrackInfoDTOList: List<TrackInfoDTO>
    private lateinit var userDataDTO: UserDataDTO

    private var isSearchInfoLoaded: Boolean = false
    private var isHistoryTrackInfoLoaded: Boolean = false
    private var isUserInfoLoaded: Boolean = false

    private var isSearch: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        swipeRefreshLayout = view.findViewById(R.id.fragmentSearch_swipeRefreshLayout)

        userImage = view.findViewById(R.id.fragmentSearch_userImage)
        userNickname = view.findViewById(R.id.fragmentSearch_userNickname)

        searchText = view.findViewById(R.id.fragmentSearch_searchText)

        recyclerView = view.findViewById(R.id.fragmentSearch_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                swipeRefreshLayout.isEnabled = !recyclerView.canScrollVertically(-1)
            }
        })

        searchInfoDTO = SearchInfoDTO()
        historyTrackInfoDTOList = emptyList()
        userDataDTO = UserDataDTO()

        isSearchInfoLoaded = false
        isHistoryTrackInfoLoaded = false
        isUserInfoLoaded = false

        isSearch = false

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

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                checkSearchField()
            }
        }

        searchText.addTextChangedListener(textWatcher)

        return view
    }

    private fun checkSearchField() {
        isSearch = searchText.text.toString().isNotBlank()
        loadData()
    }

    private fun checkIsInfoLoaded() {
        if (isSearch) {
            if (isUserInfoLoaded && isSearchInfoLoaded) {
                populateData()
                isUserInfoLoaded = false
                isSearchInfoLoaded = false
                swipeRefreshLayout.isRefreshing = false
            }
        } else {
            if (isUserInfoLoaded && isHistoryTrackInfoLoaded) {
                populateData()
                isUserInfoLoaded = false
                isHistoryTrackInfoLoaded = false
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun populateData() {
        userNickname.text = userDataDTO.nickname

        val itemClickHandler =
            ItemClickHandler(activity as MainActivity, requireContext(), recyclerView)

        if (isSearch) {
            val artistSection: Section<ArtistInfoDTO> = Section(
                title = "Найденные Артисты",
                type = SectionType.ARTIST,
                items = searchInfoDTO.foundedArtist,
                orientation = LinearLayoutManager.HORIZONTAL,
                info = searchText.text.toString()
            )

            val albumSection: Section<AlbumInfoDTO> = Section(
                title = "Найденные Альбомы",
                type = SectionType.ALBUM,
                items = searchInfoDTO.foundedAlbum,
                orientation = LinearLayoutManager.HORIZONTAL,
                info = searchText.text.toString()
            )

            val playlistSection: Section<PlaylistInfoDTO> = Section(
                title = "Найденные Плейлисты",
                type = SectionType.PLAYLIST,
                items = searchInfoDTO.foundedPlaylist,
                orientation = LinearLayoutManager.HORIZONTAL,
                info = searchText.text.toString()
            )

            val trackSection: Section<TrackInfoDTO> = Section(
                title = "Найденные Треки",
                type = SectionType.TRACK,
                items = searchInfoDTO.foundedTrack,
                orientation = LinearLayoutManager.VERTICAL,
                info = searchText.text.toString()
            )

            sectionList = listOf(artistSection, albumSection, playlistSection, trackSection)

            val sectionAdapter = SectionAdapter(
                requireContext(),
                sectionList,
                onSectionClick = { section ->
                    (activity as MainActivity).openSectionFragment(section)
                },
                onItemClick = { itemType, sectionPosition, buttonType, any, itemPosition ->
                    itemClickHandler.onItemClick(
                        itemType,
                        sectionPosition,
                        buttonType,
                        any,
                        itemPosition
                    )
                })

            recyclerView.adapter = sectionAdapter
        } else {
            val trackHistorySection: Section<TrackInfoDTO> = Section(
                title = "История Прослушивания",
                type = SectionType.TRACK,
                items = historyTrackInfoDTOList,
                orientation = LinearLayoutManager.VERTICAL,
                info = ""
            )

            sectionList = listOf(trackHistorySection)

            val sectionAdapter = SectionAdapter(
                requireContext(),
                sectionList,
                onSectionClick = { section ->
                    (activity as MainActivity).openSectionFragment(section)
                },
                onItemClick = { itemType, sectionPosition, buttonType, any, itemPosition ->
                    itemClickHandler.onItemClick(
                        itemType,
                        sectionPosition,
                        buttonType,
                        any,
                        itemPosition
                    )
                })

            recyclerView.adapter = sectionAdapter
        }

    }

    private fun loadData() {
        loadUser()

        if (isSearch) {
            loadSearch()
        } else {
            loadTrackHistory()
        }
    }

    private fun loadSearch() {
        val retrofitClient = RetrofitClient()
        val searchAPI = retrofitClient.getRetrofit(requireContext()).create(SearchAPI::class.java)

        searchAPI.searchByName(SearchRequestDTO(searchText.text.toString()))
            .enqueue(object : Callback<SearchInfoDTO> {
                override fun onResponse(
                    call: Call<SearchInfoDTO>,
                    response: Response<SearchInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        searchInfoDTO = response.body()!!
                        isSearchInfoLoaded = true
                        checkIsInfoLoaded()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Не удалось загрузить результаты поиска (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<SearchInfoDTO>, t: Throwable) {
                    Log.e("LoadSearch", "Не удалось загрузить результаты поиска", t)
                    Toast.makeText(
                        requireContext(),
                        "Не удалось загрузить результаты поиска (onFailure)",
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
                    historyTrackInfoDTOList = response.body()!!
                    isHistoryTrackInfoLoaded = true
                    checkIsInfoLoaded()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Не удалось загрузить историю прослушивания (onResponse)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<TrackInfoDTO>>, t: Throwable) {
                Log.e("LoadHistory", "Не удалось загрузить историю прослушивания", t)
                Toast.makeText(
                    requireContext(),
                    "Не удалось загрузить историю прослушивания (onFailure)",
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
                        "Не удалось загрузить пользователя (onResponse)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<UserDataDTO>, t: Throwable) {
                Log.e("LoadUser", "Не удалось загрузить пользователя", t)
                Toast.makeText(
                    requireContext(),
                    "Не удалось загрузить пользователя (onFailure)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}