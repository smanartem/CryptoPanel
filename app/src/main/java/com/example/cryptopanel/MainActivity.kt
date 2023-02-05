package com.example.cryptopanel

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.cryptopanel.databinding.ActivityMainBinding
import com.example.cryptopanel.menu.createSearchView
import com.example.cryptopanel.menu.createSwitchItem
import com.example.cryptopanel.viewModels.CryptoPanelViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

const val MODE = "mode"

class MainActivity : AppCompatActivity() {
    private val viewModel: CryptoPanelViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private val navController: NavController by lazy { createNavHost().navController }
    private val preferences: SharedPreferences by inject(named("modePrefs"))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setUpUI()
    }

    private fun setUpUI() {
        tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    findNavController(fragment_container_view.id).navigate(R.id.fragmentMain)
                } else {
                    findNavController(fragment_container_view.id).navigate(R.id.fragmentNews)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    private fun createNavHost(): NavHostFragment {
        return supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_resource, menu)

        createSwitchItem(menu, preferences)
        createSearchView(menu, viewModel)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}