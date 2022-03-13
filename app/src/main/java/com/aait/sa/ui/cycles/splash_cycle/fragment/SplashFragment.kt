package com.aait.sa.ui.cycles.splash_cycle.fragment

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.aait.domain.repository.PreferenceRepository
import com.aait.sa.databinding.FragmentSplashBinding
import com.aait.sa.ui.base.BaseFragment
import com.aait.sa.ui.base.BaseViewModel
import com.aait.sa.ui.cycles.auth_cycle.activity.AuthActivity
import com.aait.sa.ui.cycles.home_cycle.activity.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    override val viewModel by viewModels<BaseViewModel>()

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    override fun onResume() {
        super.onResume()

        lifecycleScope.launchWhenResumed {
            delay(2000)

            val token = preferenceRepository.getToken().first()

            if (token.isNotEmpty()) {
                openHomeActivity()
            } else {
                openAuthActivity()
            }
        }
    }

    private fun openHomeActivity() {
        Intent(requireActivity(), HomeActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK).also {
                startActivity(it)
            }
    }

    private fun openAuthActivity() {
        Intent(requireActivity(), AuthActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK).also {
                startActivity(it)
            }
    }

}