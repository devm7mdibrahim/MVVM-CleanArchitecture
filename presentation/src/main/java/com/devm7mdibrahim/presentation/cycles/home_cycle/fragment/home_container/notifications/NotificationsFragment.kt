package com.devm7mdibrahim.presentation.cycles.home_cycle.fragment.home_container.notifications

import androidx.fragment.app.viewModels
import com.devm7mdibrahim.presentation.base.BaseFragment
import com.devm7mdibrahim.presentation.databinding.FragmentNotificationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>(FragmentNotificationsBinding::inflate) {

    override val viewModel by viewModels<NotificationsViewModel>()

}