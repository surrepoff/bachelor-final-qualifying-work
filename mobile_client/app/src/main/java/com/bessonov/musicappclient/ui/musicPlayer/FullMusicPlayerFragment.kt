package com.bessonov.musicappclient.ui.musicPlayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.dto.TrackInfoDTO
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FullMusicPlayerFragment : BottomSheetDialogFragment() {
    private lateinit var trackTitle: TextView
    private lateinit var artistName: TextView
    private lateinit var stopButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}