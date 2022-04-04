package com.devm7mdibrahim.presentation.cycles.auth_cycle.fragment.register

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.devm7mdibrahim.domain.exceptions.ValidationException
import com.devm7mdibrahim.domain.util.DataState
import com.devm7mdibrahim.presentation.R
import com.devm7mdibrahim.presentation.base.BaseFragment
import com.devm7mdibrahim.presentation.databinding.FragmentRegisterBinding
import com.devm7mdibrahim.utils.extensions.onPrintLog
import com.devm7mdibrahim.utils.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private var avatar: String? = null
    override val viewModel by viewModels<RegisterViewModel>()

    override fun startObserver() {
        super.startObserver()
        registerListener()
    }

    private fun register() {
        lifecycleScope.launchWhenCreated {
            viewModel.registerResponse.emit(DataState.Idle)
            viewModel.register(
                name = "Mohamed",
                phone = "01024510687",
                email = "dev.m7mdibrahim@gmail.com",
                password = "123456",
                avatar = avatar
            )
        }
    }

    private fun registerListener() {
        lifecycleScope.launchWhenCreated {
            viewModel.registerResponse.collect { it ->
                when (it) {
                    is DataState.Error -> {
                        when (it.throwable) {
                            is ValidationException.InValidPhoneException -> {
                                requireContext().showToast(getString(R.string.error_invalid_phone))
                            }
                            is ValidationException.InValidPasswordException -> {
                                requireContext().showToast(getString(R.string.error_invalid_password))
                            }
                            is ValidationException.InValidNameException -> {
                                requireContext().showToast(getString(R.string.error_invalid_name))
                            }
                            is ValidationException.InValidEmailAddressException -> {
                                requireContext().showToast(getString(R.string.error_invalid_email))
                            }
                            else -> {
                                it.applyCommonSideEffects()
                            }
                        }
                    }
                    else -> {
                        it.applyCommonSideEffects {
                            it.data?.onPrintLog("userData")
                        }
                    }
                }
            }
        }
    }

}