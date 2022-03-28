package com.devm7mdibrahim.presentation.cycles.splash_cycle.fragment

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.devm7mdibrahim.presentation.base.BaseFragment
import com.devm7mdibrahim.presentation.base.BaseViewModel
import com.devm7mdibrahim.presentation.cycles.auth_cycle.activity.AuthActivity
import com.devm7mdibrahim.presentation.cycles.home_cycle.activity.HomeActivity
import com.devm7mdibrahim.presentation.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    override val viewModel by viewModels<BaseViewModel>()

    override fun onResume() {
        super.onResume()

        lifecycleScope.launchWhenResumed {
            delay(2000)

            viewModel.preferenceRepository.getToken().first { token ->
                if (token.isNotEmpty()) {
                    openHomeActivity()
                } else {
                    openAuthActivity()
                }

                return@first true
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