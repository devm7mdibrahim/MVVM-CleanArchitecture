package com.aait.sa.cycles.auth_cycle.register

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.aait.domain.exceptions.ValidationException
import com.aait.domain.util.DataState
import com.aait.sa.R
import com.aait.sa.base.BaseFragment
import com.aait.sa.databinding.FragmentRegisterBinding
import com.aait.utils.common.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private var avatar: String? = null
    override val viewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerListener()
    }

    override fun onCreateView() {
        TODO("When click register button call ${register()} function")
    }

    private fun register() {
        lifecycleScope.launchWhenCreated {
            viewModel.registerResponse.emit(DataState.Idle)
            viewModel.register(
                name= "Mohamed",
                phone = "01024510687",
                email = "mohamed@gmail.com",
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