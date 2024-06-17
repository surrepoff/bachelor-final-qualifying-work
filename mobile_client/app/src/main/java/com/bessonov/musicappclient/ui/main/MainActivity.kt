package com.bessonov.musicappclient.ui.main

import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.adapter.section.Section
import com.bessonov.musicappclient.adapter.section.SectionType
import com.bessonov.musicappclient.dto.AlbumInfoDTO
import com.bessonov.musicappclient.dto.ArtistInfoDTO
import com.bessonov.musicappclient.dto.PlaylistInfoDTO
import com.bessonov.musicappclient.dto.RecommendationInfoDTO
import com.bessonov.musicappclient.ui.album.AlbumFragment
import com.bessonov.musicappclient.ui.artist.ArtistFragment
import com.bessonov.musicappclient.ui.home.HomeFragment
import com.bessonov.musicappclient.ui.myMusic.MyMusicFragment
import com.bessonov.musicappclient.ui.playlist.PlaylistAddTrackFragment
import com.bessonov.musicappclient.ui.playlist.PlaylistEditFragment
import com.bessonov.musicappclient.ui.playlist.PlaylistFragment
import com.bessonov.musicappclient.ui.profile.ProfileFragment
import com.bessonov.musicappclient.ui.recommendation.RecommendationCreateFragment
import com.bessonov.musicappclient.ui.recommendation.RecommendationFragment
import com.bessonov.musicappclient.ui.search.SearchFragment
import com.bessonov.musicappclient.ui.section.SectionFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var mainFragmentContainerView: FragmentContainerView
    private lateinit var shortMusicPlayerFragmentContainerView: FragmentContainerView

    private lateinit var homeFragment: HomeFragment
    private lateinit var searchFragment: SearchFragment
    private lateinit var myMusicFragment: MyMusicFragment

    private var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        mainFragmentContainerView = findViewById(R.id.mainFragmentContainerView)
        shortMusicPlayerFragmentContainerView =
            findViewById(R.id.shortMusicPlayerFragmentContainerView)

        shortMusicPlayerFragmentContainerView.visibility = View.GONE

        homeFragment = HomeFragment()
        searchFragment = SearchFragment()
        myMusicFragment = MyMusicFragment()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.mainFragmentContainerView, homeFragment, "HOME").hide(homeFragment)
                add(R.id.mainFragmentContainerView, searchFragment, "SEARCH").hide(searchFragment)
                add(R.id.mainFragmentContainerView, myMusicFragment, "MY_MUSIC").hide(
                    myMusicFragment
                )
            }.commit()

            activeFragment = homeFragment
            supportFragmentManager.beginTransaction().show(homeFragment).commit()
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    showFragment(homeFragment)
                    true
                }

                R.id.navigation_search -> {
                    showFragment(searchFragment)
                    true
                }

                R.id.navigation_my_music -> {
                    showFragment(myMusicFragment)
                    true
                }

                else -> false
            }
        }
    }

    private fun showFragment(fragment: Fragment) {
        clearBackStackIfNeeded()
        if (fragment != activeFragment) {
            supportFragmentManager.beginTransaction().apply {
                hide(activeFragment!!)
                show(fragment)
            }.commit()
            activeFragment = fragment
        }
    }

    fun openSectionFragment(section: Section<*>) {
        when (section.type) {
            SectionType.PLAYLIST_CREATE -> {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.mainFragmentContainerView, PlaylistEditFragment(true))
                    addToBackStack(null)
                }.commit()
            }

            SectionType.RECOMMENDATION_CREATE -> {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.mainFragmentContainerView, RecommendationCreateFragment())
                    addToBackStack(null)
                }.commit()
            }

            else -> {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.mainFragmentContainerView, SectionFragment(section))
                    addToBackStack(null)
                }.commit()
            }
        }
    }

    fun openProfileFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFragmentContainerView, ProfileFragment())
            addToBackStack(null)
        }.commit()
    }

    fun openAlbumFragment(albumInfoDTO: AlbumInfoDTO) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFragmentContainerView, AlbumFragment(albumInfoDTO))
            addToBackStack(null)
        }.commit()
    }

    fun openArtistFragment(artistInfoDTO: ArtistInfoDTO) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFragmentContainerView, ArtistFragment(artistInfoDTO))
            addToBackStack(null)
        }.commit()
    }

    fun openPlaylistFragment(playlistInfoDTO: PlaylistInfoDTO) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFragmentContainerView, PlaylistFragment(playlistInfoDTO))
            addToBackStack(null)
        }.commit()
    }

    fun openPlaylistAddTrackFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFragmentContainerView, PlaylistAddTrackFragment())
            addToBackStack(null)
        }.commit()
    }

    fun openPlaylistEditFragment(playlistInfoDTO: PlaylistInfoDTO) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFragmentContainerView, PlaylistEditFragment(false, playlistInfoDTO))
            addToBackStack(null)
        }.commit()
    }

    fun openRecommendationFragment(recommendationInfoDTO: RecommendationInfoDTO) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFragmentContainerView, RecommendationFragment(recommendationInfoDTO))
            addToBackStack(null)
        }.commit()
    }

    private fun clearBackStackIfNeeded() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}