package com.bessonov.musicappclient.utils

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import com.bessonov.musicappclient.api.TrackAPI
import com.bessonov.musicappclient.dto.TrackInfoDTO
import com.bessonov.musicappclient.dto.UserTrackDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MusicPlayerManager(
    private val context: Context,
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

        playTrack()
    }

    private fun playTrack(currentTrackPosition: Int) {
        this.currentTrackPosition = currentTrackPosition

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

        addToTrackHistory(trackInfoDTOList[currentTrackPosition])

        isPaused = false
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

    private fun addToTrackHistory(trackInfoDTO: TrackInfoDTO) {
        val retrofitClient = RetrofitClient()
        val trackAPI =
            retrofitClient.getRetrofit(context).create(TrackAPI::class.java)

        trackAPI.addTrackToHistoryList(trackInfoDTO.track.id)
            .enqueue(object : Callback<UserTrackDTO> {
                override fun onResponse(
                    call: Call<UserTrackDTO>,
                    response: Response<UserTrackDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        //
                    } else {
                        Toast.makeText(
                            context,
                            "Failed to add track to history (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<UserTrackDTO>, t: Throwable) {
                    Log.e("AddTrackHistory", "Failed to add track history", t)
                    Toast.makeText(
                        context,
                        "Failed to add track history (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}