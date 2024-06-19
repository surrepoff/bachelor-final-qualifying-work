package com.bessonov.musicappclient.ui.musicPlayer

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import com.bessonov.musicappclient.dto.TrackInfoDTO
import com.bessonov.musicappclient.utils.ConfigManager

class MusicPlayerManager(
    context: Context,
    private val onTrackChanged: () -> Unit
) {
    private var mediaPlayer: MediaPlayer? = null
    private var isStarted: Boolean = false
    private var isPaused: Boolean = false
    private var trackInfoDTOList: List<TrackInfoDTO> = emptyList()
    private var currentTrackPosition: Int = 0
    private val configManager = ConfigManager(context)
    private val trackStreamUrl: String = configManager.getServerIp() + "/track/stream/"

    private fun isAlreadyPlaying(
        trackInfoDTOList: List<TrackInfoDTO>,
        currentTrackPosition: Int
    ): Boolean {
        return this.trackInfoDTOList == trackInfoDTOList && this.currentTrackPosition == currentTrackPosition
    }

    fun playTrack(trackInfoDTOList: List<TrackInfoDTO>, currentTrackPosition: Int) {
        if (isAlreadyPlaying(trackInfoDTOList, currentTrackPosition)) {
            if (isPlaying()) {
                pauseTrack()
            } else {
                resumeTrack()
            }
            return
        }

        this.trackInfoDTOList = trackInfoDTOList
        this.currentTrackPosition = currentTrackPosition

        Log.d("PlayTrack2", trackInfoDTOList.toString() + currentTrackPosition.toString())

        playTrack()
    }

    private fun playTrack(currentTrackPosition: Int) {
        this.currentTrackPosition = currentTrackPosition

        Log.d("PlayTrack1", trackInfoDTOList.toString() + currentTrackPosition.toString())

        playTrack()
    }

    private fun playTrack(pauseOnStart: Boolean = false) {
        isStarted = false

        val url = trackStreamUrl + trackInfoDTOList[currentTrackPosition].track.id.toString()
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setOnCompletionListener {
                onTrackCompletion()
            }
        } else {
            mediaPlayer?.reset()
        }

        mediaPlayer?.setDataSource(url)
        mediaPlayer?.prepareAsync()
        mediaPlayer?.setOnPreparedListener {
            mediaPlayer?.start()
            isStarted = true
            if (pauseOnStart)
                pauseTrack()
            onTrackChanged.invoke()
        }

        isPaused = false

        Log.d("PlayTrack0", trackInfoDTOList.toString() + currentTrackPosition.toString())
    }

    fun isMusicPlayerCreated(): Boolean {
        return mediaPlayer != null
    }

    fun pauseTrack() {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
            isPaused = true
        }
    }

    fun resumeTrack() {
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

    fun hasNextTrack(): Boolean {
        return currentTrackPosition >= 0 && currentTrackPosition + 1 < trackInfoDTOList.size
    }

    fun playNextTrack() {
        if (hasNextTrack())
            playTrack(currentTrackPosition + 1)
    }

    fun hasPreviousTrack(): Boolean {
        return currentTrackPosition > 0 && currentTrackPosition - 1 < trackInfoDTOList.size
    }

    fun playPreviousTrack() {
        if (hasPreviousTrack())
            playTrack(currentTrackPosition - 1)
    }

    fun getTrackInfoDTO(): TrackInfoDTO {
        return trackInfoDTOList[currentTrackPosition]
    }

    private fun onTrackCompletion() {
        if (isStarted) {
            if (hasNextTrack()) {
                playNextTrack()
            } else {
                playTrack(true)
            }
        }
    }
}