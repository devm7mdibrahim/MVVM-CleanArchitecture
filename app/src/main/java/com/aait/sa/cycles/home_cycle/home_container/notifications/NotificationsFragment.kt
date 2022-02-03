package com.aait.sa.cycles.home_cycle.home_container.notifications

import androidx.fragment.app.viewModels
import com.aait.sa.base.BaseFragment
import com.aait.sa.databinding.FragmentNotificationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>(FragmentNotificationsBinding::inflate) {

    override val viewModel by viewModels<NotificationsViewModel>()

    override fun afterCreateView() {
        TODO("Not yet implemented")
    }
}