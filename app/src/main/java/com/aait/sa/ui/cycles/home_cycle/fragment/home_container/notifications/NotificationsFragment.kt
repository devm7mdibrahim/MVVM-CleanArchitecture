package com.aait.sa.ui.cycles.home_cycle.fragment.home_container.notifications

import androidx.fragment.app.viewModels
import com.aait.sa.ui.base.BaseFragment
import com.aait.sa.databinding.FragmentNotificationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>(FragmentNotificationsBinding::inflate) {

    override val viewModel by viewModels<NotificationsViewModel>()

    override fun afterCreateView() {
        TODO("Not yet implemented")
    }
}