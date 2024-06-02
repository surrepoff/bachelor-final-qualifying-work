package com.bessonov.musicappclient.adapter.track;

import android.view.View;

public interface TrackItemClickListener {
    fun onTrackItemClick(view: View, position: Int)
    fun onTrackButtonClick(view: View, position: Int, buttonId: Int)
}
