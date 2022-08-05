package com.example.cryptopanel

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.cryptopanel.databinding.FragmentNewsBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_news.*

class FragmentNews : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, fragment_container_view, false)
        val view = binding.root

        binding.webView.settings.javaScriptEnabled = true
      //  binding.webView.webViewClient.shouldOverrideUrlLoading(webView, "https://forklog.com")
        binding.webView.loadUrl("https://forklog.com/news/")

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

