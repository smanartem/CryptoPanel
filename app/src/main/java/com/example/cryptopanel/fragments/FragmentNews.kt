package com.example.cryptopanel.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptopanel.adapters.NewsListAdapter
import com.example.cryptopanel.databinding.FragmentNewsBinding
import com.example.cryptopanel.viewModels.NewsViewModel
import kotlinx.android.synthetic.main.fragment_news.*

class FragmentNews : BindingFragment<FragmentNewsBinding>(FragmentNewsBinding::class) {
    private val adapter = NewsListAdapter()
    private val viewModel: NewsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel._news.observe(viewLifecycleOwner) {
            adapter.submitList(it).also { progressBar2.visibility = View.INVISIBLE }
        }
        viewModel.getAllNews()
        updateUI()

        searchNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!!.isNotEmpty()) {
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
        rv_news.layoutManager = LinearLayoutManager(context)
        rv_news.adapter = adapter
        rv_news.setHasFixedSize(true)
        progressBar2.visibility = View.VISIBLE
    }
}

