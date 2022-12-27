package com.example.cryptopanel.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptopanel.R
import com.example.cryptopanel.adapters.CryptoPanelListAdapter
import com.example.cryptopanel.databinding.FragmentMainBinding
import com.example.cryptopanel.viewModels.CryptoPanelViewModel
import kotlinx.android.synthetic.main.fragment_main.*

private const val TOPLIST = "topList"

class FragmentMain : Fragment(R.layout.fragment_main) {
    private val adapter = CryptoPanelListAdapter()
    private val viewModel: CryptoPanelViewModel by activityViewModels()
    private val prefs by lazy { requireActivity().getPreferences(MODE_PRIVATE) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view)

        viewModel.coinsList.observe(viewLifecycleOwner) {
            adapter.submitList(it).also { binding.progressBar.visibility = View.INVISIBLE }
        }
        viewModel.getAllCoins()
        updateUI(binding)
        setListeners(binding)
    }

    private fun setListeners(binding: FragmentMainBinding) {
        var topStatus = false
        var trendStatus = false

        binding.trendButton.setOnClickListener {
            setTappedColor(trendButton, trendStatus)
            if (trendStatus) {
                trendStatus = false
                viewModel.refresh()
            } else {
                trendStatus = true
                topStatus = false
                viewModel.getTrend()
                setTappedColor(topRateButton, true)
            }
        }

        binding.topRateButton.setOnClickListener {
            setTappedColor(topRateButton, topStatus)
            if (topStatus) {
                topStatus = false
                viewModel.refresh()
            } else {
                topStatus = true
                trendStatus = false
                setTappedColor(trendButton, true)
                val topArray = adapter.getListTop().toList()
                viewModel.getTop(topArray)
            }
        }
    }


    private fun updateUI(binding: FragmentMainBinding) {
        binding.apply {
            loadPrefs()

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


    override fun onPause() {
        savePrefs()
        super.onPause()
    }

    private fun savePrefs() {
        prefs.edit().remove(TOPLIST).apply()
        prefs.edit().putStringSet(TOPLIST, adapter.getListTop()).apply()
    }

    private fun loadPrefs() {
        val topList = prefs.getStringSet(TOPLIST, mutableSetOf())
        adapter.setListTop(topList!!)
    }

    private fun tapedColor(): Int {
        return resources.getColor(R.color.button_taped, null)
    }

    private fun untappedColor(): Int {
        return resources.getColor(R.color.button_untapped, null)
    }

    private fun setTappedColor(button: Button, checked: Boolean) {
        button.setBackgroundColor(if (checked) untappedColor() else tapedColor())
    }
}




