package com.example.cryptopanel.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptopanel.R
import com.example.cryptopanel.adapters.CryptoPanelAdapter
import com.example.cryptopanel.databinding.FragmentMainBinding
import com.example.cryptopanel.viewModels.CryptoPanelViewModel

class FragmentMain : Fragment(R.layout.fragment_main) {
    private val adapter = CryptoPanelAdapter()
    private val viewModel: CryptoPanelViewModel by activityViewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        viewModel.coinsList.observe(viewLifecycleOwner) {
            adapter.setCoinsList(it).also { binding.progressBar.visibility = View.INVISIBLE }
        }
        viewModel.getAllCoins()
        updateUI()
    }

    private fun updateUI() {
        binding.apply {
            rvCoins.layoutManager = LinearLayoutManager(context)
            rvCoins.adapter = adapter
            rvCoins.setHasFixedSize(true)

            val mDecoration = DividerItemDecoration(context, VERTICAL)
            ContextCompat.getDrawable(requireContext(), R.drawable.divider_drawable)
                ?.let { mDecoration.setDrawable(it) }
            rvCoins.addItemDecoration(mDecoration)
            progressBar.visibility = View.VISIBLE
        }
    }
}
