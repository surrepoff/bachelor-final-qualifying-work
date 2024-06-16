package com.bessonov.musicappclient.ui.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.ui.main.MainActivity

class PlaylistEditFragment(
    private val create: Boolean
) : Fragment() {
    private lateinit var closeButton: ImageButton
    private lateinit var headerName: TextView
    private lateinit var addButton: Button
    private lateinit var editButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist_edit, container, false)

        closeButton = view.findViewById(R.id.fragmentPlaylistEdit_closeButton)
        closeButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        headerName = view.findViewById(R.id.fragmentPlaylistEdit_headerName)
        if (create)
            headerName.text = "Создать плейлист"
        else
            headerName.text = "Изменить плейлист"

        addButton = view.findViewById(R.id.fragmentPlaylistEdit_trackAddButton)
        addButton.setOnClickListener {
            (activity as MainActivity).openPlaylistAddTrackFragment()
        }

        editButton = view.findViewById(R.id.fragmentPlaylistEdit_editButton)
        if (create)
            editButton.text = "Создать"
        else
            editButton.text = "Изменить"

        return view
    }

}