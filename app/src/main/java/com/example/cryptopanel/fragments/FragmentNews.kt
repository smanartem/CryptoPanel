package com.example.cryptopanel.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptopanel.R
import com.example.cryptopanel.adapters.NewsAdapter
import com.example.cryptopanel.databinding.FragmentNewsBinding
import com.example.cryptopanel.viewModels.NewsViewModel

class FragmentNews : Fragment(R.layout.fragment_news) {
    private val adapter = NewsAdapter()
    private val viewModel: NewsViewModel by activityViewModels()
    //lateinit var только в тестах или инжекте (если по какой-то причине нелзя заинжектить через констуктор)
    private lateinit var binding: FragmentNewsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)

        viewModel.news.observe(viewLifecycleOwner) {
            adapter.setNews(it).also { binding.progressBar2.visibility = View.INVISIBLE }
        }
        viewModel.getAllNews()
        updateUI()

        binding.searchNews.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query!!.isNotEmpty()){
                    viewModel.getSearchNews(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun updateUI() {
        binding.apply {
            rvNews.layoutManager = LinearLayoutManager(context)
            rvNews.adapter = adapter
            rvNews.setHasFixedSize(true)
            progressBar2.visibility = View.VISIBLE
        }
    }
}

