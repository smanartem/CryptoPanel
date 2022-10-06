package com.example.cryptopanel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptopanel.adapters.NewsAdapter
import com.example.cryptopanel.databinding.FragmentNewsBinding
import com.example.cryptopanel.viewModels.CryptoPanelViewModel
import kotlinx.android.synthetic.main.activity_main.*

class FragmentNews : Fragment() {
    private val adapter = NewsAdapter()
    private val viewModel: CryptoPanelViewModel by activityViewModels()
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.news.observe(this) {
                adapter.setNews(it).also { binding.progressBar2.visibility = View.INVISIBLE }
        }
        viewModel.getAllNews()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, fragment_container_view, false)

        binding.rvNews.layoutManager = LinearLayoutManager(context)
        binding.rvNews.adapter = adapter
        binding.rvNews.setHasFixedSize(true)
        binding.progressBar2.visibility = View.VISIBLE
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

