package com.devm7mdibrahim.presentation.cycles.home_cycle.fragment.home_container.profile

import androidx.fragment.app.viewModels
import com.devm7mdibrahim.presentation.base.BaseFragment
import com.devm7mdibrahim.presentation.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    override val viewModel by viewModels<ProfileViewModel>()

    override fun afterCreateView() {

    }
}