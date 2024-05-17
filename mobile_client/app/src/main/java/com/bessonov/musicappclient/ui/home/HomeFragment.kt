package com.bessonov.musicappclient.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.adapter.track.TrackAdapter
import com.bessonov.musicappclient.dto.TrackInfoDTO
import com.bessonov.musicappclient.retrofit.RetrofitService
import com.bessonov.musicappclient.retrofit.TrackInfoAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return view
    }

    override fun onResume() {
        super.onResume()
        loadTracks()
    }

    private fun populateListView(trackInfoDTOList: List<TrackInfoDTO>) {
        val trackAdapter = TrackAdapter(trackInfoDTOList)
        recyclerView.adapter = trackAdapter
    }

    private fun loadTracks() {
        val retrofitService = RetrofitService()
        val trackInfoAPI = retrofitService.retrofit.create(TrackInfoAPI::class.java)
        trackInfoAPI.getAll().enqueue(object : Callback<List<TrackInfoDTO>> {
            override fun onResponse(call: Call<List<TrackInfoDTO>>, response: Response<List<TrackInfoDTO>>) {
                if (response.isSuccessful && response.body() != null) {
                    populateListView(response.body()!!)
                } else {
                    Toast.makeText(requireContext(), "Failed to load tracks (onResponse)", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<TrackInfoDTO>>, t: Throwable) {
                Log.e("LoadTracks", "Failed to load tracks", t)
                Toast.makeText(requireContext(), "Failed to load tracks (onFailure)", Toast.LENGTH_SHORT).show()
            }
        })
    }
}