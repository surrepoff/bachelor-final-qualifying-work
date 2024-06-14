package com.bessonov.musicappclient.ui.main

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.adapter.section.Section
import com.bessonov.musicappclient.ui.home.HomeFragment
import com.bessonov.musicappclient.ui.myMusic.MyMusicFragment
import com.bessonov.musicappclient.ui.profile.ProfileFragment
import com.bessonov.musicappclient.ui.search.SearchFragment
import com.bessonov.musicappclient.ui.section.SectionFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fragmentContainerView: FragmentContainerView

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
        fragmentContainerView = findViewById(R.id.fragmentContainerView)

        homeFragment = HomeFragment()
        searchFragment = SearchFragment()
        myMusicFragment = MyMusicFragment()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.fragmentContainerView, homeFragment, "HOME").hide(homeFragment)
                add(R.id.fragmentContainerView, searchFragment, "SEARCH").hide(searchFragment)
                add(R.id.fragmentContainerView, myMusicFragment, "MY_MUSIC").hide(myMusicFragment)
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
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, SectionFragment(section))
            addToBackStack(null)
        }.commit()
    }

    fun openProfileFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, ProfileFragment())
            addToBackStack(null)
        }.commit()
    }

    private fun clearBackStackIfNeeded() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}