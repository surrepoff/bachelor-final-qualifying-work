package com.bessonov.musicappclient.ui.recommendation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.adapter.genre.GenreAdapter
import com.bessonov.musicappclient.api.GenreAPI
import com.bessonov.musicappclient.api.RecommendationAPI
import com.bessonov.musicappclient.dto.GenreDTO
import com.bessonov.musicappclient.dto.RecommendationCreateDTO
import com.bessonov.musicappclient.dto.RecommendationInfoDTO
import com.bessonov.musicappclient.ui.main.MainActivity
import com.bessonov.musicappclient.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommendationCreateFragment : Fragment() {
    private lateinit var closeButton: ImageButton
    private lateinit var sizeText: EditText
    private lateinit var familiarityValue: TextView
    private lateinit var familiaritySeekBar: SeekBar
    private lateinit var genreRecyclerView: RecyclerView
    private lateinit var createButton: Button
    private lateinit var cancelButton: Button

    private lateinit var genreDTOList: List<GenreDTO>

    private lateinit var genreAdapter: GenreAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recommendation_create, container, false)

        closeButton = view.findViewById(R.id.fragmentRecommendationCreate_closeButton)

        closeButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        sizeText = view.findViewById(R.id.fragmentRecommendationCreate_sizeText)

        familiarityValue = view.findViewById(R.id.fragmentRecommendationCreate_familiarityValue)
        familiaritySeekBar = view.findViewById(R.id.fragmentRecommendationCreate_familiaritySeekBar)

        familiaritySeekBar.progress = 50
        familiarityValue.text = java.lang.String(familiaritySeekBar.progress.toString() + "%")

        familiaritySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                familiarityValue.text = java.lang.String("$progress%")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        createButton = view.findViewById(R.id.fragmentRecommendationCreate_createButton)
        createButton.setOnClickListener {
            createRecommendation()
        }

        cancelButton = view.findViewById(R.id.fragmentRecommendationCreate_cancelButton)
        cancelButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        genreRecyclerView = view.findViewById(R.id.fragmentRecommendationCreate_genreRecyclerView)
        genreRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        genreDTOList = emptyList()
        loadGenres()

        return view
    }

    private fun populateData() {
        genreAdapter = GenreAdapter(requireContext(), genreDTOList)
        genreRecyclerView.adapter = genreAdapter
    }

    private fun loadGenres() {
        val retrofitClient = RetrofitClient()
        val genreAPI = retrofitClient.getRetrofit(requireContext()).create(GenreAPI::class.java)

        genreAPI.getAll().enqueue(object : Callback<List<GenreDTO>> {
            override fun onResponse(
                call: Call<List<GenreDTO>>,
                response: Response<List<GenreDTO>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    genreDTOList = response.body()!!
                    populateData()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Failed to load genres (onResponse)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<GenreDTO>>, t: Throwable) {
                Log.e("LoadGenre", "Failed to load genres", t)
                Toast.makeText(
                    requireContext(),
                    "Failed to load genres (onFailure)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun createRecommendation() {
        Log.d("CreateRecommendation", "Start createRecommendation")
        val checkedGenreDTOList = genreAdapter.getCheckedItems()

        if (checkedGenreDTOList.isEmpty()) {
            Log.d("CreateRecommendation", "List is empty")
            return
        }

        val recommendationCreateDTO = RecommendationCreateDTO()
        recommendationCreateDTO.size = if (sizeText.text.isNotBlank()) {
            sizeText.text.toString().toInt()
        } else {
            0
        }
        recommendationCreateDTO.userId = -1
        recommendationCreateDTO.extractionTypeId = 0
        recommendationCreateDTO.familiarityPercentage = familiaritySeekBar.progress * 0.01
        recommendationCreateDTO.genreId = checkedGenreDTOList.map { it.id }

        Log.d("CreateRecommendation", recommendationCreateDTO.toString())

        val retrofitClient = RetrofitClient()
        val recommendationAPI =
            retrofitClient.getRetrofit(requireContext()).create(RecommendationAPI::class.java)

        recommendationAPI.createUserRecommendation(recommendationCreateDTO)
            .enqueue(object : Callback<RecommendationInfoDTO> {
                override fun onResponse(
                    call: Call<RecommendationInfoDTO>,
                    response: Response<RecommendationInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val recommendation = response.body()!!
                        activity?.supportFragmentManager?.popBackStack()
                        (activity as MainActivity).openRecommendationFragment(recommendation)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to create recommendation (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<RecommendationInfoDTO>, t: Throwable) {
                    Log.e("CreateRecommendation", "Failed to create recommendation", t)
                    Toast.makeText(
                        requireContext(),
                        "Failed to create recommendation (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}