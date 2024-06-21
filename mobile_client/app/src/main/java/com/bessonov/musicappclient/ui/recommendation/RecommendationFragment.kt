package com.bessonov.musicappclient.ui.recommendation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.adapter.track.TrackAdapter
import com.bessonov.musicappclient.api.RecommendationAPI
import com.bessonov.musicappclient.api.RetrofitClient
import com.bessonov.musicappclient.api.TrackAPI
import com.bessonov.musicappclient.dto.PlaylistInfoDTO
import com.bessonov.musicappclient.dto.RecommendationInfoDTO
import com.bessonov.musicappclient.dto.TrackInfoDTO
import com.bessonov.musicappclient.ui.main.MainActivity
import com.bessonov.musicappclient.utils.ItemClickHandler
import com.bessonov.musicappclient.utils.ItemType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class RecommendationFragment(
    private var recommendationInfoDTO: RecommendationInfoDTO
) : Fragment() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var closeButton: ImageButton
    private lateinit var recommendationAdd: Button
    private lateinit var recommendationName: TextView
    private lateinit var ownerName: TextView
    private lateinit var deleteButton: ImageButton
    private lateinit var likeButton: ImageButton
    private lateinit var dislikeButton: ImageButton
    private lateinit var createText: TextView
    private lateinit var trackRecyclerView: RecyclerView

    private lateinit var trackInfoDTOList: List<TrackInfoDTO>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recommendation, container, false)

        swipeRefreshLayout = view.findViewById(R.id.fragmentRecommendation_swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }

        closeButton = view.findViewById(R.id.fragmentRecommendation_closeButton)
        closeButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        recommendationAdd = view.findViewById(R.id.fragmentRecommendation_recommendationAdd)
        recommendationAdd.setOnClickListener {
            addRecommendation()
        }

        recommendationName = view.findViewById(R.id.fragmentRecommendation_recommendationName)

        ownerName = view.findViewById(R.id.fragmentRecommendation_ownerName)

        deleteButton = view.findViewById(R.id.fragmentRecommendation_deleteButton)
        deleteButton.setOnClickListener {
            deleteRecommendation()
        }

        likeButton = view.findViewById(R.id.fragmentRecommendation_likeButton)
        likeButton.setOnClickListener {
            when (recommendationInfoDTO.rating.name) {
                "Like" -> {
                    rateRecommendation(0)
                }

                else -> {
                    rateRecommendation(1)
                }
            }
        }

        dislikeButton = view.findViewById(R.id.fragmentRecommendation_dislikeButton)
        dislikeButton.setOnClickListener {
            when (recommendationInfoDTO.rating.name) {
                "Dislike" -> {
                    rateRecommendation(0)
                }

                else -> {
                    rateRecommendation(-1)
                }
            }
        }

        createText = view.findViewById(R.id.fragmentRecommendation_createText)

        trackRecyclerView = view.findViewById(R.id.fragmentRecommendation_trackRecyclerView)
        trackRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadData()

        return view
    }

    private fun populateData() {
        swipeRefreshLayout.isRefreshing = false

        val name = "Rec №" + recommendationInfoDTO.recommendation.id
        recommendationName.text = name
        ownerName.text = recommendationInfoDTO.user.nickname

        when (recommendationInfoDTO.rating.name) {
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
        createText.text = dateFormat.format(recommendationInfoDTO.recommendation.creationDate)

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
        loadRecommendation()
    }

    private fun loadRecommendation() {
        val retrofitClient = RetrofitClient()
        val recommendationAPI =
            retrofitClient.getRetrofit(requireContext()).create(RecommendationAPI::class.java)

        recommendationAPI.getByUserRecommendationId(recommendationInfoDTO.recommendation.id)
            .enqueue(object : Callback<RecommendationInfoDTO> {
                override fun onResponse(
                    call: Call<RecommendationInfoDTO>,
                    response: Response<RecommendationInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        recommendationInfoDTO = response.body()!!
                        loadTracks()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Не удалось загрузить рекомендацию (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<RecommendationInfoDTO>, t: Throwable) {
                    Log.e("LoadRecommendation", "Не удалось загрузить рекомендацию", t)
                    Toast.makeText(
                        requireContext(),
                        "Не удалось загрузить рекомендацию (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun loadTracks() {
        val retrofitClient = RetrofitClient()
        val trackAPI = retrofitClient.getRetrofit(requireContext()).create(TrackAPI::class.java)

        trackAPI.getByTrackIdList(recommendationInfoDTO.trackId)
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

    private fun deleteRecommendation() {
        val retrofitClient = RetrofitClient()
        val recommendationAPI =
            retrofitClient.getRetrofit(requireContext()).create(RecommendationAPI::class.java)

        recommendationAPI.deleteUserRecommendation(recommendationInfoDTO.recommendation.id)
            .enqueue(object : Callback<RecommendationInfoDTO> {
                override fun onResponse(
                    call: Call<RecommendationInfoDTO>,
                    response: Response<RecommendationInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        activity?.supportFragmentManager?.popBackStack()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Не удалось удалить рекомендацию (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<RecommendationInfoDTO>, t: Throwable) {
                    Log.e("DeleteRecommendation", "Не удалось удалить рекомендацию", t)
                    Toast.makeText(
                        requireContext(),
                        "Не удалось удалить рекомендацию (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun addRecommendation() {
        val retrofitClient = RetrofitClient()
        val recommendationAPI =
            retrofitClient.getRetrofit(requireContext()).create(RecommendationAPI::class.java)

        recommendationAPI.addUserRecommendationAsPlaylist(recommendationInfoDTO.recommendation.id)
            .enqueue(object : Callback<PlaylistInfoDTO> {
                override fun onResponse(
                    call: Call<PlaylistInfoDTO>,
                    response: Response<PlaylistInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        activity?.supportFragmentManager?.popBackStack()
                        (activity as MainActivity).openPlaylistFragment(response.body()!!)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Не удалось добавить рекомендацию (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<PlaylistInfoDTO>, t: Throwable) {
                    Log.e("AddRecommendation", "Не удалось добавить рекомендацию", t)
                    Toast.makeText(
                        requireContext(),
                        "Не удалось добавить рекомендацию (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun rateRecommendation(rateId: Int) {
        val retrofitClient = RetrofitClient()
        val recommendationAPI =
            retrofitClient.getRetrofit(requireContext()).create(RecommendationAPI::class.java)

        recommendationAPI.rateUserRecommendation(recommendationInfoDTO.recommendation.id, rateId)
            .enqueue(object : Callback<RecommendationInfoDTO> {
                override fun onResponse(
                    call: Call<RecommendationInfoDTO>,
                    response: Response<RecommendationInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        recommendationInfoDTO = response.body()!!
                        populateData()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Не удалось оценить рекомендацию (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<RecommendationInfoDTO>, t: Throwable) {
                    Log.e("RateRecommendation", "Не удалось оценить рекомендацию", t)
                    Toast.makeText(
                        requireContext(),
                        "Не удалось оценить рекомендацию (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}