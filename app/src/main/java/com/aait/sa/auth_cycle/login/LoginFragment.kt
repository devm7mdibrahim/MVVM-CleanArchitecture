package com.aait.sa.auth_cycle.login

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.aait.domain.entities.AuthData
import com.aait.domain.exceptions.ValidationException
import com.aait.domain.util.DataState
import com.aait.sa.R
import com.aait.sa.base.BaseFragment
import com.aait.sa.databinding.FragmentLoginBinding
import com.aait.utils.common.fetchText
import com.aait.utils.common.showToast
import com.aait.utils.common.toJson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    override val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginListener()
    }


    override fun onCreateView() {
        TODO("When click login button call ${login()} function")
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

    private fun loginListener() {
        lifecycleScope.launchWhenCreated {
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
                                it.applyCommonSideEffects()
                            }
                        }
                    }
                    else -> {
                        it.applyCommonSideEffects {
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