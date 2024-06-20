package com.bessonov.musicappclient.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bessonov.musicappclient.adapter.album.AlbumAdapter
import com.bessonov.musicappclient.adapter.artist.ArtistAdapter
import com.bessonov.musicappclient.adapter.playlist.PlaylistAdapter
import com.bessonov.musicappclient.adapter.recommendation.RecommendationAdapter
import com.bessonov.musicappclient.adapter.section.SectionViewHolder
import com.bessonov.musicappclient.adapter.track.TrackAdapter
import com.bessonov.musicappclient.api.AlbumAPI
import com.bessonov.musicappclient.api.ArtistAPI
import com.bessonov.musicappclient.api.PlaylistAPI
import com.bessonov.musicappclient.api.RecommendationAPI
import com.bessonov.musicappclient.api.RetrofitClient
import com.bessonov.musicappclient.api.TrackAPI
import com.bessonov.musicappclient.dto.AlbumInfoDTO
import com.bessonov.musicappclient.dto.ArtistInfoDTO
import com.bessonov.musicappclient.dto.PlaylistInfoDTO
import com.bessonov.musicappclient.dto.RecommendationInfoDTO
import com.bessonov.musicappclient.dto.TrackInfoDTO
import com.bessonov.musicappclient.ui.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemClickHandler(
    private val activity: MainActivity,
    private val context: Context,
    private val recyclerView: RecyclerView
) {
    fun onItemClick(
        itemType: ItemType,
        sectionPosition: Int,
        buttonType: ButtonType,
        any: Any,
        itemPosition: Int
    ) {
        when (itemType) {
            ItemType.ALBUM -> {
                when (buttonType) {
                    ButtonType.ITEM -> {
                        activity.openAlbumFragment(any as AlbumInfoDTO)
                    }

                    ButtonType.ADD -> {
                        if ((any as AlbumInfoDTO).isAdded.isAdded) {
                            removeAlbum(any, sectionPosition, itemPosition)
                        } else {
                            addAlbum(any, sectionPosition, itemPosition)
                        }
                    }

                    ButtonType.LIKE -> {
                        when ((any as AlbumInfoDTO).rating.name) {
                            "Like" -> {
                                rateAlbum(0, any, sectionPosition, itemPosition)
                            }

                            else -> {
                                rateAlbum(1, any, sectionPosition, itemPosition)
                            }
                        }
                    }

                    ButtonType.DISLIKE -> {
                        when ((any as AlbumInfoDTO).rating.name) {
                            "Dislike" -> {
                                rateAlbum(0, any, sectionPosition, itemPosition)
                            }

                            else -> {
                                rateAlbum(-1, any, sectionPosition, itemPosition)
                            }
                        }
                    }
                }
            }

            ItemType.ARTIST -> {
                when (buttonType) {
                    ButtonType.ITEM -> {
                        activity.openArtistFragment(any as ArtistInfoDTO)
                    }

                    ButtonType.ADD -> {
                        if ((any as ArtistInfoDTO).isAdded.isAdded) {
                            removeArtist(any, sectionPosition, itemPosition)
                        } else {
                            addArtist(any, sectionPosition, itemPosition)
                        }
                    }

                    ButtonType.LIKE -> {
                        when ((any as ArtistInfoDTO).rating.name) {
                            "Like" -> {
                                rateArtist(0, any, sectionPosition, itemPosition)
                            }

                            else -> {
                                rateArtist(1, any, sectionPosition, itemPosition)
                            }
                        }
                    }

                    ButtonType.DISLIKE -> {
                        when ((any as ArtistInfoDTO).rating.name) {
                            "Dislike" -> {
                                rateArtist(0, any, sectionPosition, itemPosition)
                            }

                            else -> {
                                rateArtist(-1, any, sectionPosition, itemPosition)
                            }
                        }
                    }
                }
            }

            ItemType.PLAYLIST -> {
                when (buttonType) {
                    ButtonType.ITEM -> {
                        activity.openPlaylistFragment(any as PlaylistInfoDTO)
                    }

                    ButtonType.ADD -> {
                        if ((any as PlaylistInfoDTO).isAdded.isAdded) {
                            removePlaylist(any, sectionPosition, itemPosition)
                        } else {
                            addPlaylist(any, sectionPosition, itemPosition)
                        }
                    }

                    ButtonType.LIKE -> {
                        when ((any as PlaylistInfoDTO).rating.name) {
                            "Like" -> {
                                ratePlaylist(0, any, sectionPosition, itemPosition)
                            }

                            else -> {
                                ratePlaylist(1, any, sectionPosition, itemPosition)
                            }
                        }
                    }

                    ButtonType.DISLIKE -> {
                        when ((any as PlaylistInfoDTO).rating.name) {
                            "Dislike" -> {
                                ratePlaylist(0, any, sectionPosition, itemPosition)
                            }

                            else -> {
                                ratePlaylist(-1, any, sectionPosition, itemPosition)
                            }
                        }
                    }
                }
            }

            ItemType.RECOMMENDATION -> {
                when (buttonType) {
                    ButtonType.ITEM -> {
                        activity.openRecommendationFragment(any as RecommendationInfoDTO)
                    }

                    ButtonType.LIKE -> {
                        when ((any as RecommendationInfoDTO).rating.name) {
                            "Like" -> {
                                rateRecommendation(0, any, sectionPosition, itemPosition)
                            }

                            else -> {
                                rateRecommendation(1, any, sectionPosition, itemPosition)
                            }
                        }
                    }

                    ButtonType.DISLIKE -> {
                        when ((any as RecommendationInfoDTO).rating.name) {
                            "Dislike" -> {
                                rateRecommendation(0, any, sectionPosition, itemPosition)
                            }

                            else -> {
                                rateRecommendation(-1, any, sectionPosition, itemPosition)
                            }
                        }
                    }

                    else -> {
                        //
                    }
                }
            }

            ItemType.TRACK -> {
                when (buttonType) {
                    ButtonType.ITEM -> {
                        val trackInfoDTOList = if (sectionPosition != -1) {
                            val sectionViewHolder =
                                ((recyclerView.findViewHolderForAdapterPosition(sectionPosition)) as SectionViewHolder)
                            val trackAdapter =
                                sectionViewHolder.recyclerView.adapter as TrackAdapter

                            trackAdapter.getTrackInfoDTOList()
                        } else {
                            val trackAdapter = recyclerView.adapter as TrackAdapter

                            trackAdapter.getTrackInfoDTOList()
                        }

                        activity.openShortMusicPlayerFragment(trackInfoDTOList, itemPosition)
                    }

                    ButtonType.ADD -> {
                        if ((any as TrackInfoDTO).isAdded.isAdded) {
                            removeTrack(any, sectionPosition, itemPosition)
                        } else {
                            addTrack(any, sectionPosition, itemPosition)
                        }
                    }

                    ButtonType.LIKE -> {
                        when ((any as TrackInfoDTO).rating.name) {
                            "Like" -> {
                                rateTrack(0, any, sectionPosition, itemPosition)
                            }

                            else -> {
                                rateTrack(1, any, sectionPosition, itemPosition)
                            }
                        }
                    }

                    ButtonType.DISLIKE -> {
                        when ((any as TrackInfoDTO).rating.name) {
                            "Dislike" -> {
                                rateTrack(0, any, sectionPosition, itemPosition)
                            }

                            else -> {
                                rateTrack(-1, any, sectionPosition, itemPosition)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun updateAlbum(albumInfoDTO: AlbumInfoDTO, sectionPosition: Int, itemPosition: Int) {
        if (sectionPosition != -1) {
            val sectionViewHolder =
                ((recyclerView.findViewHolderForAdapterPosition(sectionPosition)) as SectionViewHolder)
            val albumAdapter = sectionViewHolder.recyclerView.adapter as AlbumAdapter
            albumAdapter.updateItem(itemPosition, albumInfoDTO)
        } else {
            val albumAdapter = recyclerView.adapter as AlbumAdapter
            albumAdapter.updateItem(itemPosition, albumInfoDTO)
        }
    }

    private fun removeAlbum(any: Any, sectionPosition: Int, itemPosition: Int) {
        val retrofitClient = RetrofitClient()
        val albumAPI =
            retrofitClient.getRetrofit(context).create(AlbumAPI::class.java)

        albumAPI.removeAlbumFromUserList((any as AlbumInfoDTO).album.id)
            .enqueue(object : Callback<AlbumInfoDTO> {
                override fun onResponse(
                    call: Call<AlbumInfoDTO>,
                    response: Response<AlbumInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val albumInfoDTO = response.body()!!
                        updateAlbum(albumInfoDTO, sectionPosition, itemPosition)
                    } else {
                        Toast.makeText(
                            context,
                            "Failed to remove album (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<AlbumInfoDTO>, t: Throwable) {
                    Log.e("RemoveAlbum", "Failed to remove album", t)
                    Toast.makeText(
                        context,
                        "Failed to remove album (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun addAlbum(any: Any, sectionPosition: Int, itemPosition: Int) {
        val retrofitClient = RetrofitClient()
        val albumAPI =
            retrofitClient.getRetrofit(context).create(AlbumAPI::class.java)

        albumAPI.addAlbumToUserList((any as AlbumInfoDTO).album.id)
            .enqueue(object : Callback<AlbumInfoDTO> {
                override fun onResponse(
                    call: Call<AlbumInfoDTO>,
                    response: Response<AlbumInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val albumInfoDTO = response.body()!!
                        updateAlbum(albumInfoDTO, sectionPosition, itemPosition)
                    } else {
                        Toast.makeText(
                            context,
                            "Failed to add album (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<AlbumInfoDTO>, t: Throwable) {
                    Log.e("AddAlbum", "Failed to add album", t)
                    Toast.makeText(
                        context,
                        "Failed to add album (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun rateAlbum(rateId: Int, any: Any, sectionPosition: Int, itemPosition: Int) {
        val retrofitClient = RetrofitClient()
        val albumAPI =
            retrofitClient.getRetrofit(context).create(AlbumAPI::class.java)

        albumAPI.rateAlbum((any as AlbumInfoDTO).album.id, rateId)
            .enqueue(object : Callback<AlbumInfoDTO> {
                override fun onResponse(
                    call: Call<AlbumInfoDTO>,
                    response: Response<AlbumInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val albumInfoDTO = response.body()!!
                        updateAlbum(albumInfoDTO, sectionPosition, itemPosition)
                    } else {
                        Toast.makeText(
                            context,
                            "Failed to rate album (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<AlbumInfoDTO>, t: Throwable) {
                    Log.e("RateAlbum", "Failed to rate album", t)
                    Toast.makeText(
                        context,
                        "Failed to rate album (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun updateArtist(
        artistInfoDTO: ArtistInfoDTO,
        sectionPosition: Int,
        itemPosition: Int
    ) {
        if (sectionPosition != -1) {
            val sectionViewHolder =
                ((recyclerView.findViewHolderForAdapterPosition(sectionPosition)) as SectionViewHolder)
            val artistAdapter = sectionViewHolder.recyclerView.adapter as ArtistAdapter
            artistAdapter.updateItem(itemPosition, artistInfoDTO)
        } else {
            val artistAdapter = recyclerView.adapter as ArtistAdapter
            artistAdapter.updateItem(itemPosition, artistInfoDTO)
        }
    }

    private fun removeArtist(any: Any, sectionPosition: Int, itemPosition: Int) {
        val retrofitClient = RetrofitClient()
        val artistAPI =
            retrofitClient.getRetrofit(context).create(ArtistAPI::class.java)

        artistAPI.removeArtistFromUserList((any as ArtistInfoDTO).artist.id)
            .enqueue(object : Callback<ArtistInfoDTO> {
                override fun onResponse(
                    call: Call<ArtistInfoDTO>,
                    response: Response<ArtistInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val artistInfoDTO = response.body()!!
                        updateArtist(artistInfoDTO, sectionPosition, itemPosition)
                    } else {
                        Toast.makeText(
                            context,
                            "Failed to remove artist (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ArtistInfoDTO>, t: Throwable) {
                    Log.e("RemoveArtist", "Failed to remove artist", t)
                    Toast.makeText(
                        context,
                        "Failed to remove artist (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun addArtist(any: Any, sectionPosition: Int, itemPosition: Int) {
        val retrofitClient = RetrofitClient()
        val artistAPI =
            retrofitClient.getRetrofit(context).create(ArtistAPI::class.java)

        artistAPI.addArtistToUserList((any as ArtistInfoDTO).artist.id)
            .enqueue(object : Callback<ArtistInfoDTO> {
                override fun onResponse(
                    call: Call<ArtistInfoDTO>,
                    response: Response<ArtistInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val artistInfoDTO = response.body()!!
                        updateArtist(artistInfoDTO, sectionPosition, itemPosition)
                    } else {
                        Toast.makeText(
                            context,
                            "Failed to add artist (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ArtistInfoDTO>, t: Throwable) {
                    Log.e("AddArtist", "Failed to add artist", t)
                    Toast.makeText(
                        context,
                        "Failed to add artist (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun rateArtist(rateId: Int, any: Any, sectionPosition: Int, itemPosition: Int) {
        val retrofitClient = RetrofitClient()
        val artistAPI =
            retrofitClient.getRetrofit(context).create(ArtistAPI::class.java)

        artistAPI.rateArtist((any as ArtistInfoDTO).artist.id, rateId)
            .enqueue(object : Callback<ArtistInfoDTO> {
                override fun onResponse(
                    call: Call<ArtistInfoDTO>,
                    response: Response<ArtistInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val artistInfoDTO = response.body()!!
                        updateArtist(artistInfoDTO, sectionPosition, itemPosition)
                    } else {
                        Toast.makeText(
                            context,
                            "Failed to rate artist (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ArtistInfoDTO>, t: Throwable) {
                    Log.e("RateArtist", "Failed to rate artist", t)
                    Toast.makeText(
                        context,
                        "Failed to rate artist (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun updatePlaylist(
        playlistInfoDTO: PlaylistInfoDTO,
        sectionPosition: Int,
        itemPosition: Int
    ) {
        if (sectionPosition != -1) {
            val sectionViewHolder =
                ((recyclerView.findViewHolderForAdapterPosition(sectionPosition)) as SectionViewHolder)
            val playlistAdapter = sectionViewHolder.recyclerView.adapter as PlaylistAdapter
            playlistAdapter.updateItem(itemPosition, playlistInfoDTO)
        } else {
            val playlistAdapter = recyclerView.adapter as PlaylistAdapter
            playlistAdapter.updateItem(itemPosition, playlistInfoDTO)
        }
    }

    private fun removePlaylist(any: Any, sectionPosition: Int, itemPosition: Int) {
        val retrofitClient = RetrofitClient()
        val playlistAPI =
            retrofitClient.getRetrofit(context).create(PlaylistAPI::class.java)

        playlistAPI.removePlaylistFromUserList((any as PlaylistInfoDTO).playlist.id)
            .enqueue(object : Callback<PlaylistInfoDTO> {
                override fun onResponse(
                    call: Call<PlaylistInfoDTO>,
                    response: Response<PlaylistInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val playlistInfoDTO = response.body()!!
                        updatePlaylist(playlistInfoDTO, sectionPosition, itemPosition)
                    } else {
                        Toast.makeText(
                            context,
                            "Failed to remove playlist (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<PlaylistInfoDTO>, t: Throwable) {
                    Log.e("RemovePlaylist", "Failed to remove playlist", t)
                    Toast.makeText(
                        context,
                        "Failed to remove playlist (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun addPlaylist(any: Any, sectionPosition: Int, itemPosition: Int) {
        val retrofitClient = RetrofitClient()
        val playlistAPI =
            retrofitClient.getRetrofit(context).create(PlaylistAPI::class.java)

        playlistAPI.addPlaylistToUserList((any as PlaylistInfoDTO).playlist.id)
            .enqueue(object : Callback<PlaylistInfoDTO> {
                override fun onResponse(
                    call: Call<PlaylistInfoDTO>,
                    response: Response<PlaylistInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val playlistInfoDTO = response.body()!!
                        updatePlaylist(playlistInfoDTO, sectionPosition, itemPosition)
                    } else {
                        Toast.makeText(
                            context,
                            "Failed to add playlist (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<PlaylistInfoDTO>, t: Throwable) {
                    Log.e("AddPlaylist", "Failed to add playlist", t)
                    Toast.makeText(
                        context,
                        "Failed to add playlist (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun ratePlaylist(rateId: Int, any: Any, sectionPosition: Int, itemPosition: Int) {
        val retrofitClient = RetrofitClient()
        val playlistAPI =
            retrofitClient.getRetrofit(context).create(PlaylistAPI::class.java)

        playlistAPI.ratePlaylist((any as PlaylistInfoDTO).playlist.id, rateId)
            .enqueue(object : Callback<PlaylistInfoDTO> {
                override fun onResponse(
                    call: Call<PlaylistInfoDTO>,
                    response: Response<PlaylistInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val playlistInfoDTO = response.body()!!
                        updatePlaylist(playlistInfoDTO, sectionPosition, itemPosition)
                    } else {
                        Toast.makeText(
                            context,
                            "Failed to rate playlist (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<PlaylistInfoDTO>, t: Throwable) {
                    Log.e("RatePlaylist", "Failed to rate playlist", t)
                    Toast.makeText(
                        context,
                        "Failed to rate playlist (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun updateRecommendation(
        recommendationInfoDTO: RecommendationInfoDTO,
        sectionPosition: Int,
        itemPosition: Int
    ) {
        if (sectionPosition != -1) {
            val sectionViewHolder =
                ((recyclerView.findViewHolderForAdapterPosition(sectionPosition)) as SectionViewHolder)
            val recommendationAdapter =
                sectionViewHolder.recyclerView.adapter as RecommendationAdapter
            recommendationAdapter.updateItem(itemPosition, recommendationInfoDTO)
        } else {
            val recommendationAdapter = recyclerView.adapter as RecommendationAdapter
            recommendationAdapter.updateItem(itemPosition, recommendationInfoDTO)
        }
    }

    private fun rateRecommendation(rateId: Int, any: Any, sectionPosition: Int, itemPosition: Int) {
        val retrofitClient = RetrofitClient()
        val recommendationAPI =
            retrofitClient.getRetrofit(context).create(RecommendationAPI::class.java)

        recommendationAPI.rateUserRecommendation(
            (any as RecommendationInfoDTO).recommendation.id,
            rateId
        )
            .enqueue(object : Callback<RecommendationInfoDTO> {
                override fun onResponse(
                    call: Call<RecommendationInfoDTO>,
                    response: Response<RecommendationInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val recommendationInfoDTO = response.body()!!
                        updateRecommendation(recommendationInfoDTO, sectionPosition, itemPosition)
                    } else {
                        Toast.makeText(
                            context,
                            "Failed to rate recommendation (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<RecommendationInfoDTO>, t: Throwable) {
                    Log.e("RateRecommendation", "Failed to rate recommendation", t)
                    Toast.makeText(
                        context,
                        "Failed to rate recommendation (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun updateTrack(trackInfoDTO: TrackInfoDTO, sectionPosition: Int, itemPosition: Int) {
        if (sectionPosition != -1) {
            val sectionViewHolder =
                ((recyclerView.findViewHolderForAdapterPosition(sectionPosition)) as SectionViewHolder)
            val trackAdapter = sectionViewHolder.recyclerView.adapter as TrackAdapter
            trackAdapter.updateItem(itemPosition, trackInfoDTO)
        } else {
            val trackAdapter = recyclerView.adapter as TrackAdapter
            trackAdapter.updateItem(itemPosition, trackInfoDTO)
        }
    }

    private fun removeTrack(any: Any, sectionPosition: Int, itemPosition: Int) {
        val retrofitClient = RetrofitClient()
        val trackAPI =
            retrofitClient.getRetrofit(context).create(TrackAPI::class.java)

        trackAPI.removeTrackFromUserList((any as TrackInfoDTO).track.id)
            .enqueue(object : Callback<TrackInfoDTO> {
                override fun onResponse(
                    call: Call<TrackInfoDTO>,
                    response: Response<TrackInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val trackInfoDTO = response.body()!!
                        updateTrack(trackInfoDTO, sectionPosition, itemPosition)
                    } else {
                        Toast.makeText(
                            context,
                            "Failed to remove track (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<TrackInfoDTO>, t: Throwable) {
                    Log.e("RemoveTrack", "Failed to remove track", t)
                    Toast.makeText(
                        context,
                        "Failed to remove track (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun addTrack(any: Any, sectionPosition: Int, itemPosition: Int) {
        val retrofitClient = RetrofitClient()
        val trackAPI =
            retrofitClient.getRetrofit(context).create(TrackAPI::class.java)

        trackAPI.addTrackToUserList((any as TrackInfoDTO).track.id)
            .enqueue(object : Callback<TrackInfoDTO> {
                override fun onResponse(
                    call: Call<TrackInfoDTO>,
                    response: Response<TrackInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val trackInfoDTO = response.body()!!
                        updateTrack(trackInfoDTO, sectionPosition, itemPosition)
                    } else {
                        Toast.makeText(
                            context,
                            "Failed to add track (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<TrackInfoDTO>, t: Throwable) {
                    Log.e("AddTrack", "Failed to add track", t)
                    Toast.makeText(
                        context,
                        "Failed to add track (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun rateTrack(rateId: Int, any: Any, sectionPosition: Int, itemPosition: Int) {
        val retrofitClient = RetrofitClient()
        val trackAPI =
            retrofitClient.getRetrofit(context).create(TrackAPI::class.java)

        trackAPI.rateTrack((any as TrackInfoDTO).track.id, rateId)
            .enqueue(object : Callback<TrackInfoDTO> {
                override fun onResponse(
                    call: Call<TrackInfoDTO>,
                    response: Response<TrackInfoDTO>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val trackInfoDTO = response.body()!!
                        updateTrack(trackInfoDTO, sectionPosition, itemPosition)
                    } else {
                        Toast.makeText(
                            context,
                            "Failed to rate track (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<TrackInfoDTO>, t: Throwable) {
                    Log.e("RateTrack", "Failed to rate track", t)
                    Toast.makeText(
                        context,
                        "Failed to rate track (onFailure)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}
