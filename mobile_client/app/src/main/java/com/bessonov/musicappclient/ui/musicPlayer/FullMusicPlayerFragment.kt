package com.bessonov.musicappclient.ui.musicPlayer

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.api.RetrofitClient
import com.bessonov.musicappclient.api.TrackAPI
import com.bessonov.musicappclient.dto.TrackInfoDTO
import com.bessonov.musicappclient.manager.ConfigManager
import com.bessonov.musicappclient.manager.SessionManager
import com.bessonov.musicappclient.ui.main.MainActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FullMusicPlayerFragment(
    private val context: Context,
    private var trackInfoDTO: TrackInfoDTO
) : Fragment() {
    private lateinit var closeButton: ImageButton
    private lateinit var trackImage: ImageView
    private lateinit var trackName: TextView
    private lateinit var artistName: TextView
    private lateinit var addButton: ImageButton
    private lateinit var likeButton: ImageButton
    private lateinit var dislikeButton: ImageButton
    private lateinit var progressBar: SeekBar
    private lateinit var currentTime: TextView
    private lateinit var fullTime: TextView
    private lateinit var stopButton: ImageButton
    private lateinit var previousButton: ImageButton
    private lateinit var nextButton: ImageButton

    private var updateProgressBarThread: Thread? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_full_music_player, container, false)

        closeButton = view.findViewById(R.id.fragmentFullMusicPlayer_closeButton)
        closeButton.setOnClickListener {
            (activity as MainActivity).closeFullMusicPlayerFragment()
        }

        trackImage = view.findViewById(R.id.fragmentFullMusicPlayer_trackImage)
        trackName = view.findViewById(R.id.fragmentFullMusicPlayer_trackName)
        artistName = view.findViewById(R.id.fragmentFullMusicPlayer_artistName)

        addButton = view.findViewById(R.id.fragmentFullMusicPlayer_addButton)
        addButton.setOnClickListener {
            if (trackInfoDTO.isAdded.isAdded) {
                removeTrack()
            } else {
                addTrack()
            }
        }

        likeButton = view.findViewById(R.id.fragmentFullMusicPlayer_likeButton)
        likeButton.setOnClickListener {
            when (trackInfoDTO.rating.name) {
                "Like" -> {
                    rateTrack(0)
                }

                else -> {
                    rateTrack(1)
                }
            }
        }

        dislikeButton = view.findViewById(R.id.fragmentFullMusicPlayer_dislikeButton)
        dislikeButton.setOnClickListener {
            when (trackInfoDTO.rating.name) {
                "Dislike" -> {
                    rateTrack(0)
                }

                else -> {
                    rateTrack(-1)
                }
            }
        }

        progressBar = view.findViewById(R.id.fragmentFullMusicPlayer_progressBar)
        progressBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    (activity as MainActivity).musicPlayerManager.seekTo(progress.toLong())
                    updateCurrentTime()
                    updateFullTime()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        currentTime = view.findViewById(R.id.fragmentFullMusicPlayer_currentTime)
        fullTime = view.findViewById(R.id.fragmentFullMusicPlayer_fullTime)

        stopButton = view.findViewById(R.id.fragmentFullMusicPlayer_stopButton)
        stopButton.setOnClickListener {
            if ((activity as MainActivity).musicPlayerManager.isPlaying()) {
                (activity as MainActivity).musicPlayerManager.pauseTrack()
            } else {
                (activity as MainActivity).musicPlayerManager.resumeTrack()
            }
            updateStopButton()
        }

        previousButton = view.findViewById(R.id.fragmentFullMusicPlayer_previousButton)
        previousButton.setOnClickListener {
            (activity as MainActivity).musicPlayerManager.playPreviousTrack()
        }

        nextButton = view.findViewById(R.id.fragmentFullMusicPlayer_nextButton)
        nextButton.setOnClickListener {
            (activity as MainActivity).musicPlayerManager.playNextTrack()
        }

        startProgressBarUpdater()

        populateDate()
        loadTrack()

        return view
    }

    fun setTrackInfo(trackInfoDTO: TrackInfoDTO) {
        this.trackInfoDTO = trackInfoDTO
        populateDate()
        loadTrack()
    }

    private fun populateDate() {
        loadTrackImage()

        trackName.text = trackInfoDTO.track.name

        var name = ""
        name += trackInfoDTO.artist.joinToString(", ") { it.name }
        if (trackInfoDTO.featuredArtist.size != 0) {
            name += " feat. " + trackInfoDTO.featuredArtist.joinToString(", ") { it.name }
        }

        artistName.text = name

        updateAddButton()
        updateLikeDislikeButton()

        updateCurrentTime()
        updateFullTime()

        progressBar.max = (activity as MainActivity).musicPlayerManager.getDuration().toInt()

        updateStopButton()
        updateNextPreviousButton()
    }

    private fun updateAddButton() {
        if (trackInfoDTO.isAdded.isAdded) {
            addButton.setImageResource(R.drawable.ic_check)
        } else {
            addButton.setImageResource(R.drawable.ic_plus)
        }
    }

    private fun updateLikeDislikeButton() {
        when (trackInfoDTO.rating.name) {
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
    }

    private fun updateCurrentTime() {
        val currentTimeValue = (activity as MainActivity).musicPlayerManager.getCurrentTime() / 1000
        currentTime.text = String.format(
            "%02d:%02d",
            currentTimeValue / 60,
            currentTimeValue % 60
        )
    }

    private fun updateFullTime() {
        val fullTimeValue = (activity as MainActivity).musicPlayerManager.getDuration() / 1000
        fullTime.text = String.format(
            "%02d:%02d",
            fullTimeValue / 60,
            fullTimeValue % 60
        )
    }

    private fun updateStopButton() {
        if ((activity as MainActivity).musicPlayerManager.isPlaying()) {
            stopButton.setImageResource(R.drawable.ic_stop)
        } else {
            stopButton.setImageResource(R.drawable.ic_play)
        }
    }

    private fun updateNextPreviousButton() {
        if ((activity as MainActivity).musicPlayerManager.hasNextTrack()) {
            nextButton.visibility = View.VISIBLE
        } else {
            nextButton.visibility = View.INVISIBLE
        }

        if ((activity as MainActivity).musicPlayerManager.hasPreviousTrack()) {
            previousButton.visibility = View.VISIBLE
        } else {
            previousButton.visibility = View.INVISIBLE
        }
    }

    private fun loadTrack() {
        val retrofitClient = RetrofitClient()
        val trackAPI = retrofitClient.getRetrofit(context).create(TrackAPI::class.java)

        trackAPI.getByTrackId(trackInfoDTO.track.id).enqueue(object : Callback<TrackInfoDTO> {
            override fun onResponse(
                call: Call<TrackInfoDTO>,
                response: Response<TrackInfoDTO>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    trackInfoDTO = response.body()!!
                    populateDate()
                } else {
                    Toast.makeText(
                        context,
                        "Не удалось загрузить трек (onResponse)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<TrackInfoDTO>, t: Throwable) {
                Log.e("LoadTrack", "Не удалось загрузить трек", t)
                Toast.makeText(
                    context,
                    "Не удалось загрузить трек (onFailure)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun loadTrackImage() {
        val configManager = ConfigManager(context)
        val sessionManager = SessionManager(context)

        val glideUrl = GlideUrl(
            configManager.getServerIp() + "/image/track/" + trackInfoDTO.track.id,
            LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer " + sessionManager.fetchAuthToken())
                .build()
        )

        view?.let {
            Glide.with(it)
                .load(glideUrl)
                .placeholder(R.drawable.default_album)
                .into(trackImage)
        }
    }

    private fun startProgressBarUpdater() {
        progressBar.max = (activity as MainActivity).musicPlayerManager.getDuration().toInt()
        updateProgressBarThread = Thread {
            try {
                while (true) {
                    if ((activity as MainActivity).musicPlayerManager.isMusicPlayerCreated()) {
                        val message = Message()
                        message.what =
                            (activity as MainActivity).musicPlayerManager.getCurrentTime().toInt()
                        handler.sendMessage(message)
                    }
                    Thread.sleep(100)
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        updateProgressBarThread?.start()
    }

    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            progressBar.progress = msg.what
            currentTime.text = String.format(
                "%02d:%02d",
                msg.what / 1000 / 60,
                msg.what / 1000 % 60
            )
            if (activity != null)
                updateStopButton()
        }
    }

    override fun onDestroy() {
        updateProgressBarThread?.interrupt()
        super.onDestroy()
    }

    private fun removeTrack() {
        val retrofitClient = RetrofitClient()
        val trackAPI =
            retrofitClient.getRetrofit(context).create(TrackAPI::class.java)

        trackAPI.removeTrackFromUserList(trackInfoDTO.track.id)
            .enqueue(object : Callback<TrackInfoDTO> {
                override fun onResponse(
                    call: Call<TrackInfoDTO>,
                    response: Response<TrackInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        trackInfoDTO = response.body()!!
                        updateAddButton()
                    } else {
                        Toast.makeText(
                            context,
                            "Не удалось убрать трек (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<TrackInfoDTO>, t: Throwable) {
                    Log.e("RemoveTrack", "Не удалось убрать трек", t)
                    Toast.makeText(
                        context,
                        "Не удалось убрать трек (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun addTrack() {
        val retrofitClient = RetrofitClient()
        val trackAPI =
            retrofitClient.getRetrofit(context).create(TrackAPI::class.java)

        trackAPI.addTrackToUserList(trackInfoDTO.track.id)
            .enqueue(object : Callback<TrackInfoDTO> {
                override fun onResponse(
                    call: Call<TrackInfoDTO>,
                    response: Response<TrackInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        trackInfoDTO = response.body()!!
                        updateAddButton()
                    } else {
                        Toast.makeText(
                            context,
                            "Не удалось добавить трек (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<TrackInfoDTO>, t: Throwable) {
                    Log.e("AddTrack", "Не удалось добавить трек", t)
                    Toast.makeText(
                        context,
                        "Не удалось добавить трек (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun rateTrack(rateId: Int) {
        val retrofitClient = RetrofitClient()
        val trackAPI =
            retrofitClient.getRetrofit(context).create(TrackAPI::class.java)

        trackAPI.rateTrack(trackInfoDTO.track.id, rateId)
            .enqueue(object : Callback<TrackInfoDTO> {
                override fun onResponse(
                    call: Call<TrackInfoDTO>,
                    response: Response<TrackInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        trackInfoDTO = response.body()!!
                        updateLikeDislikeButton()
                    } else {
                        Toast.makeText(
                            context,
                            "Не удалось оценить трек (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<TrackInfoDTO>, t: Throwable) {
                    Log.e("RateTrack", "Не удалось оценить трек", t)
                    Toast.makeText(
                        context,
                        "Не удалось оценить трек (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}