package com.devm7mdibrahim.presentation.cycles.home_cycle.fragment.home_container.home

import androidx.fragment.app.viewModels
import com.devm7mdibrahim.presentation.base.BaseFragment
import com.devm7mdibrahim.presentation.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel by viewModels<HomeViewModel>()

    override fun afterCreateView() {

    }
}