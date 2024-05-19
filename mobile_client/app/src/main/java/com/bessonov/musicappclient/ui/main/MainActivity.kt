package com.bessonov.musicappclient.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.bessonov.musicappclient.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var tabLayout : TabLayout
    private lateinit var viewPager : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.activityMain_TabLayout)
        viewPager = findViewById(R.id.activityMain_ViewPager2)

        viewPager.isUserInputEnabled = false
        viewPager.adapter = ViewPagerAdapter(this)

        val tabTitles = arrayOf("Home", "Search", "My music")
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}