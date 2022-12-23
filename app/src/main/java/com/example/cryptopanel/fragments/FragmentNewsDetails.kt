package com.example.cryptopanel.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.cryptopanel.R
import com.example.cryptopanel.databinding.FragmentNewsDetailsBinding

class FragmentNewsDetails: Fragment(R.layout.fragment_news_details) {
    //lateinit var только в тестах или инжекте (если по какой-то причине нелзя заинжектить через констуктор)
    private lateinit var binding: FragmentNewsDetailsBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsDetailsBinding.bind(view)

        //захардкоженный ключ
        val url = arguments?.getString("url").toString()
        binding.webView.loadUrl(url)
    }
}