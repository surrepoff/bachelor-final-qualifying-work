package com.bessonov.musicappclient.utils

import android.content.Context
import com.bessonov.musicappclient.dto.AlbumInfoDTO
import com.bessonov.musicappclient.dto.ArtistInfoDTO
import com.bessonov.musicappclient.dto.PlaylistInfoDTO
import com.bessonov.musicappclient.dto.RecommendationInfoDTO
import com.bessonov.musicappclient.ui.main.MainActivity

class ItemClickHandler(
    private val activity: MainActivity,
    private val context: Context
) {
    fun onItemClick(itemType: ItemType, buttonType: ButtonType, any: Any) {
        when (itemType) {
            ItemType.ALBUM -> {
                when (buttonType) {
                    ButtonType.ITEM -> {
                        activity.openAlbumFragment(any as AlbumInfoDTO)
                    }

                    ButtonType.ADD -> {
                        // Handle ADD
                    }

                    ButtonType.LIKE -> {
                        // Handle LIKE
                    }

                    ButtonType.DISLIKE -> {
                        // Handle DISLIKE
                    }
                }
            }

            ItemType.ARTIST -> {
                when (buttonType) {
                    ButtonType.ITEM -> {
                        activity.openArtistFragment(any as ArtistInfoDTO)
                    }

                    ButtonType.ADD -> {
                        // Handle ADD
                    }

                    ButtonType.LIKE -> {
                        // Handle LIKE
                    }

                    ButtonType.DISLIKE -> {
                        // Handle DISLIKE
                    }
                }
            }

            ItemType.PLAYLIST -> {
                when (buttonType) {
                    ButtonType.ITEM -> {
                        activity.openPlaylistFragment(any as PlaylistInfoDTO)
                    }

                    ButtonType.ADD -> {
                        // Handle ADD
                    }

                    ButtonType.LIKE -> {
                        // Handle LIKE
                    }

                    ButtonType.DISLIKE -> {
                        // Handle DISLIKE
                    }
                }
            }

            ItemType.RECOMMENDATION -> {
                when (buttonType) {
                    ButtonType.ITEM -> {
                        activity.openRecommendationFragment(any as RecommendationInfoDTO)
                    }

                    ButtonType.ADD -> {
                        // Handle ADD
                    }

                    ButtonType.LIKE -> {
                        // Handle LIKE
                    }

                    ButtonType.DISLIKE -> {
                        // Handle DISLIKE
                    }
                }
            }

            ItemType.TRACK -> {
                // Handle TRACK
            }
        }
    }
}
