package com.example.cryptopanel.fragments

import android.os.Bundle
import android.view.View
import com.example.cryptopanel.databinding.FragmentNewsDetailsBinding
import kotlinx.android.synthetic.main.fragment_news_details.*

class FragmentNewsDetails: BindingFragment<FragmentNewsDetailsBinding>(FragmentNewsDetailsBinding::class) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = arguments?.getString("url").toString()
        webView.loadUrl(url)
    }
}