package com.example.cryptopanel.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptopanel.R
import com.example.cryptopanel.adapters.CryptoPanelListAdapter
import com.example.cryptopanel.databinding.FragmentMainBinding
import com.example.cryptopanel.viewModels.CryptoPanelViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.core.qualifier.named

const val TOPLIST = "topList"

class FragmentMain : BindingFragment<FragmentMainBinding>(FragmentMainBinding::class) {
    private val adapter = CryptoPanelListAdapter()
    private val viewModel: CryptoPanelViewModel by activityViewModel()
    private val prefs: SharedPreferences by inject(named("topListPrefs"))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel._coinsList.observe(viewLifecycleOwner) {
            adapter.submitList(it).also { binding.progressBar.visibility = View.INVISIBLE }
        }
        viewModel.getAllCoins()
        updateUI()
        setListeners()
    }

    private fun setListeners() {
        var topStatus = false
        var trendStatus = false

        trendButton.setOnClickListener {
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

        topRateButton.setOnClickListener {
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


    private fun updateUI() {
        loadPrefs()

        rv_coins.layoutManager = LinearLayoutManager(context)
        rv_coins.adapter = adapter
        rv_coins.setHasFixedSize(true)

        val mDecoration = DividerItemDecoration(context, VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.divider_drawable)
            ?.let { mDecoration.setDrawable(it) }
        rv_coins.addItemDecoration(mDecoration)
        progressBar.visibility = View.VISIBLE
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




