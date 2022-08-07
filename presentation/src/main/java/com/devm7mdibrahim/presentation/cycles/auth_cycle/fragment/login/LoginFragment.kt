package com.devm7mdibrahim.presentation.cycles.auth_cycle.fragment.login

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.devm7mdibrahim.domain.entities.AuthData
import com.devm7mdibrahim.domain.exceptions.ValidationException
import com.devm7mdibrahim.domain.util.DataState
import com.devm7mdibrahim.presentation.R
import com.devm7mdibrahim.presentation.base.BaseFragment
import com.devm7mdibrahim.presentation.databinding.FragmentLoginBinding
import com.devm7mdibrahim.utils.extensions.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    override val viewModel by viewModels<LoginViewModel>()

    override fun startObserver() {
        super.startObserver()
        loginObserver()
    }

    override fun handleClicks() {
        super.handleClicks()

        binding.btnLogin.onClick {
            login()
        }
    }

    private fun login() {
        lifecycleScope.launchWhenCreated {
            viewModel.loginResponse.emit(DataState.Idle)
            viewModel.login(
                phone = binding.etPhone.fetchText(),
                password = binding.etPassword.fetchText(),
                deviceId = viewModel.preferenceRepository.getFirebaseToken().first(),
            )
        }
    }

    private fun loginObserver() {
        collectLifecycleFlow(viewModel.loginResponse) {
            when (it) {
                is DataState.Error -> {
                    when (it.throwable) {
                        is ValidationException.InValidPhoneException -> {
                            requireContext().showToast(getString(R.string.error_invalid_phone))
                        }
                        is ValidationException.InValidPasswordException -> {
                            requireContext().showToast(getString(R.string.error_invalid_password))
                        }
                        else -> {
                            it.applyCommonSideEffects()
                        }
                    }
                }
                else -> {
                    it.applyCommonSideEffects { response ->
                        response.data?.let { userData -> saveUserData(userData) }
                    }
                }
            }
        }
    }

    private fun saveUserData(data: AuthData) {
        lifecycleScope.launchWhenCreated {
            viewModel.preferenceRepository.setToken(data.token)
            viewModel.preferenceRepository.setUserData(data.user.toJson())
        }
    }
}