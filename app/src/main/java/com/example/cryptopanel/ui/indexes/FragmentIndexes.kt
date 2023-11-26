package com.example.cryptopanel.ui.indexes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cryptopanel.data.data.model.CoinDataModel
import com.example.cryptopanel.databinding.FragmentIndexesBinding
import com.example.cryptopanel.ui.mainScreen.CryptoPanelViewModel
import com.example.cryptopanel.utils.INDEX10
import com.example.cryptopanel.utils.INDEX100
import com.example.cryptopanel.utils.toFormat
import kotlinx.android.synthetic.main.fragment_indexes.index10
import kotlinx.android.synthetic.main.fragment_indexes.index100
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class FragmentIndexes : Fragment() {
    private var _binding: FragmentIndexesBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: CryptoPanelViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIndexesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.coinsListLive.observe(viewLifecycleOwner) {
            index10.text = buildString {
                append("index10 \n")
                append(createIndex(it, INDEX10).toFormat())
            }
            index100.text = buildString {
                append("index100 \n")
                append(createIndex(it, INDEX100).toFormat())
            }

        }
    }
}

private fun createIndex(list: List<CoinDataModel>, numberOfIndex: Int): Double {
    var counter = 0
    var summa = 0.0

    while (counter < numberOfIndex) {
        summa += list[counter].current_price
        counter++
    }
    return summa / counter
}