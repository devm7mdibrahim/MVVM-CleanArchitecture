package com.aait.sa.cycles.home_cycle.home_container.home

import androidx.fragment.app.viewModels
import com.aait.sa.base.BaseFragment
import com.aait.sa.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel by viewModels<HomeViewModel>()

    override fun afterCreateView() {
        TODO("Not yet implemented")
    }
}