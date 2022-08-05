package com.example.cryptopanel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptopanel.databinding.FragmentMainBinding
import com.example.cryptopanel.viewModels.CryptoPanelViewModel
import kotlinx.android.synthetic.main.activity_main.*

class FragmentMain : Fragment() {
    private val adapter = CryptoPanelAdapter()
    private val model: CryptoPanelViewModel by activityViewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.coinsList.observe(this) {
            adapter.setCoinsList(it)
            binding.progressBar.isVisible = false
        }
        model.getAllCoins()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, fragment_container_view, false)
        val view = binding.root
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)

        val mDecoration = DividerItemDecoration(context, VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.divider_drawable)
            ?.let { mDecoration.setDrawable(it) }
        binding.recyclerView.addItemDecoration(mDecoration)
        binding.progressBar.isVisible = true
        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

