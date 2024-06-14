package com.bessonov.musicappclient.ui.musicPlayer

import android.content.Context
import android.media.MediaPlayer
import com.bessonov.musicappclient.dto.TrackInfoDTO
import com.bessonov.musicappclient.utils.ConfigManager

class MusicPlayerManager(private val context: Context) : MediaPlayer.OnCompletionListener {
    private var mediaPlayer: MediaPlayer? = null
    private var isPaused: Boolean = false
    private var trackInfoDTOList: List<TrackInfoDTO>? = null
    private var currentTrackPosition: Int? = null
    private val configManager = ConfigManager(context)
    private val trackStreamUrl: String = configManager.getServerIp() + "/track/stream/"

    fun playTrack(trackInfoDTOList: List<TrackInfoDTO>, currentTrackPosition: Int) {
        val url = trackStreamUrl + trackInfoDTOList[currentTrackPosition].track.id.toString()
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setDataSource(url)
            mediaPlayer?.prepare()
            mediaPlayer?.start()
        } else {
            mediaPlayer?.reset()
            mediaPlayer?.setDataSource(url)
            mediaPlayer?.prepare()
            mediaPlayer?.start()
        }
        isPaused = false
    }

    fun pauseSong() {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
            isPaused = true
        }
    }

    fun resumeSong() {
        if (isPaused) {
            mediaPlayer?.start()
            isPaused = false
        }
    }

    fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }

    fun releaseMediaPlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun getCurrentTime(): Long {
        return mediaPlayer!!.currentPosition.toLong()
    }

    fun seekTo(value: Long) {
        mediaPlayer!!.seekTo(value.toInt())
    }

    fun getDuration(): Long {
        return mediaPlayer!!.duration.toLong()
    }

    override fun onCompletion(mp: MediaPlayer?) {
        TODO("Not yet implemented")
    }
}