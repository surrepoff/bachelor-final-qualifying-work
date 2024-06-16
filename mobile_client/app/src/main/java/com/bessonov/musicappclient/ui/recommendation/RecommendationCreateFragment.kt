package com.bessonov.musicappclient.ui.recommendation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.bessonov.musicappclient.R

class RecommendationCreateFragment : Fragment() {
    private lateinit var closeButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recommendation_create, container, false)

        closeButton = view.findViewById(R.id.fragmentRecommendationCreate_closeButton)
        closeButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        return view
    }
}