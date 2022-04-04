package com.devm7mdibrahim.presentation.cycles.home_cycle.fragment.home_container

import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.devm7mdibrahim.presentation.R
import com.devm7mdibrahim.presentation.base.BaseFragment
import com.devm7mdibrahim.presentation.databinding.FragmentHomeContainerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeContainerFragment :
    BaseFragment<FragmentHomeContainerBinding>(FragmentHomeContainerBinding::inflate) {

    override val viewModel by viewModels<HomeContainerViewModel>()

    override fun onResume() {
        super.onResume()
        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        val navController = Navigation.findNavController(requireActivity(), R.id.home_container)
        NavigationUI.setupWithNavController(binding.navView, navController)
        binding.navView.setupWithNavController(navController)
    }
}