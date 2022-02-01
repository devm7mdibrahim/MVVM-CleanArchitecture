package com.aait.sa.home_cycle.home_container.notifications

import androidx.fragment.app.viewModels
import com.aait.sa.base.BaseFragment
import com.aait.sa.databinding.FragmentNotificationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>(FragmentNotificationsBinding::inflate) {

    override val viewModel by viewModels<NotificationsViewModel>()

    override fun onCreateView() {
        TODO("Not yet implemented")
    }
}