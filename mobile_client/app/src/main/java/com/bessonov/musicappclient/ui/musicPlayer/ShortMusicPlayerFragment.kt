package com.bessonov.musicappclient.ui.musicPlayer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.dto.TrackInfoDTO
import com.bessonov.musicappclient.ui.main.MainActivity
import com.bessonov.musicappclient.utils.ConfigManager
import com.bessonov.musicappclient.utils.SessionManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders

class ShortMusicPlayerFragment : Fragment() {
    private lateinit var progressBar: SeekBar
    private lateinit var trackImage: ImageView
    private lateinit var trackName: TextView
    private lateinit var artistName: TextView
    private lateinit var stopButton: ImageButton

    private lateinit var trackInfoDTO: TrackInfoDTO

    private var updateProgressBarThread: Thread? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_short_music_player, container, false)

        progressBar = view.findViewById(R.id.fragmentShortMusicPlayer_progressBar)
        trackImage = view.findViewById(R.id.fragmentShortMusicPlayer_trackImage)
        trackName = view.findViewById(R.id.fragmentShortMusicPlayer_trackName)
        artistName = view.findViewById(R.id.fragmentShortMusicPlayer_artistName)
        stopButton = view.findViewById(R.id.fragmentShortMusicPlayer_stopButton)

        trackInfoDTO = TrackInfoDTO()

        view.setOnClickListener {
            (activity as MainActivity).openFullMusicPlayerFragment()
        }

        progressBar.setOnTouchListener { _, _ -> true }

        stopButton.setOnClickListener {
            if ((activity as MainActivity).musicPlayerManager.isPlaying()) {
                (activity as MainActivity).musicPlayerManager.pauseTrack()
            } else {
                (activity as MainActivity).musicPlayerManager.resumeTrack()
            }
            updateStopButton()
        }

        return view
    }

    fun setTrackInfo(trackInfoDTO: TrackInfoDTO) {
        startProgressBarUpdater()
        this.trackInfoDTO = trackInfoDTO
        populateDate()
    }

    private fun populateDate() {
        trackName.text = trackInfoDTO.track.name

        var name = ""
        name += trackInfoDTO.artist.joinToString(", ") { it.name }
        if (trackInfoDTO.featuredArtist.size != 0) {
            name += " feat. " + trackInfoDTO.featuredArtist.joinToString(", ") { it.name }
        }

        artistName.text = name

        updateStopButton()

        loadTrackImage()
    }

    private fun loadTrackImage() {
        val configManager = ConfigManager(requireContext())
        val sessionManager = SessionManager(requireContext())

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

    private fun updateStopButton() {
        if ((activity as MainActivity).musicPlayerManager.isPlaying()) {
            stopButton.setImageResource(R.drawable.ic_stop)
        } else {
            stopButton.setImageResource(R.drawable.ic_play)
        }
    }

    private fun startProgressBarUpdater() {
        progressBar.max = (activity as MainActivity).musicPlayerManager.getDuration().toInt()
        updateProgressBarThread = Thread {
            try {
                while (true) {
                    if ((activity as MainActivity).musicPlayerManager.isMusicPlayerCreated()) {
                        val currentPosition =
                            (activity as MainActivity).musicPlayerManager.getCurrentTime()
                        progressBar.progress = currentPosition.toInt()
                        updateStopButton()
                    }
                    Thread.sleep(100)
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        updateProgressBarThread?.start()
    }

    override fun onDestroy() {
        updateProgressBarThread?.interrupt()
        super.onDestroy()
    }
}