package com.bessonov.musicappclient.ui.musicPlayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.dto.TrackInfoDTO

class ShortMusicPlayerFragment : Fragment() {
    private lateinit var trackTitle: TextView
    private lateinit var artistName: TextView
    private lateinit var stopButton: Button

    private var trackInfo: TrackInfoDTO? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_short_music_player, container, false)

        trackTitle = view.findViewById(R.id.fragmentShortMusicPlayer_trackName)
        artistName = view.findViewById(R.id.fragmentShortMusicPlayer_artistName)
        stopButton = view.findViewById(R.id.fragmentShortMusicPlayer_stopButton)

        trackInfo?.let {
            trackTitle.text = it.track.name
            artistName.text = it.artist.toString()
        }

        stopButton.setOnClickListener {
            // Остановка трека
        }

        return view
    }
}