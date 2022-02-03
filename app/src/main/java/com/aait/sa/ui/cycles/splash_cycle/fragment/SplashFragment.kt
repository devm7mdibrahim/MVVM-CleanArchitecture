package com.aait.sa.ui.cycles.splash_cycle.fragment

import androidx.fragment.app.viewModels
import com.aait.sa.databinding.FragmentSplashBinding
import com.aait.sa.ui.base.BaseFragment
import com.aait.sa.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    override val viewModel by viewModels<BaseViewModel>()

    override fun startObserver() {
        super.startObserver()
    }

    override fun afterCreateView() {

    }

}