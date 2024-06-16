package com.bessonov.musicappclient.ui.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.ui.main.MainActivity

class PlaylistAddTrackFragment : Fragment() {
    private lateinit var closeButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist_add_track, container, false)

        closeButton = view.findViewById(R.id.fragmentPlaylistAddTrack_closeButton)
        closeButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        return view
    }
}