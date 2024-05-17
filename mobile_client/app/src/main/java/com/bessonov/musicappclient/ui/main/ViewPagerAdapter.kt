package com.bessonov.musicappclient.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bessonov.musicappclient.ui.home.HomeFragment
import com.bessonov.musicappclient.ui.myMusic.MyMusicFragment
import com.bessonov.musicappclient.ui.search.SearchFragment

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> SearchFragment()
            2 -> MyMusicFragment()
            else -> HomeFragment()
        }
    }
}