package com.devm7mdibrahim.presentation.cycles.auth_cycle.fragment.login

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.devm7mdibrahim.domain.entities.AuthData
import com.devm7mdibrahim.domain.exceptions.ValidationException
import com.devm7mdibrahim.domain.util.DataState
import com.devm7mdibrahim.presentation.R
import com.devm7mdibrahim.presentation.base.BaseFragment
import com.devm7mdibrahim.presentation.databinding.FragmentLoginBinding
import com.devm7mdibrahim.presentation.utils.applyCommonSideEffects
import com.devm7mdibrahim.utils.extensions.fetchText
import com.devm7mdibrahim.utils.extensions.onClick
import com.devm7mdibrahim.utils.extensions.showToast
import com.devm7mdibrahim.utils.extensions.toJson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
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
        lifecycleScope.launchWhenStarted {
            viewModel.loginResponse.collect { it ->
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
                                it.applyCommonSideEffects(this@LoginFragment)
                            }
                        }
                    }
                    else -> {
                        it.applyCommonSideEffects(this@LoginFragment) {
                            it.data?.let { userData -> saveUserData(userData) }
                        }
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