package com.example.cryptopanel.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.cryptopanel.R
import com.example.cryptopanel.databinding.FragmentNewsDetailsBinding

class FragmentNewsDetails: Fragment(R.layout.fragment_news_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentNewsDetailsBinding.bind(view)

        val url = arguments?.getString("url").toString()
        binding.webView.loadUrl(url)
    }
}