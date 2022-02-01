package com.aait.sa.base

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.aait.domain.entities.BaseResponse
import com.aait.domain.exceptions.NetworkExceptions
import com.aait.domain.util.DataState
import com.aait.sa.auth_cycle.AuthActivity
import com.aait.utils.common.NetworkHelper
import com.aait.utils.common.ProgressUtil
import com.aait.utils.common.ToastType
import com.aait.utils.common.showToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

abstract class BaseBottomSheetFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) :
    BottomSheetDialogFragment() {

    @Inject
    lateinit var progressUtil: ProgressUtil

    @Inject
    lateinit var networkHelper: NetworkHelper

    abstract val viewModel: BaseViewModel

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (_binding == null) {
            _binding = inflate.invoke(inflater, container, false)
        }
        requireDialog().window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, com.aait.utils.R.style.bottomSheetDialogStyle)
    }

    fun <T> DataState<T>.applyCommonSideEffects(
        showLoading: Boolean = true,
        showSuccessToast: Boolean = true,
        showErrorToast: Boolean = true,
        onSuccess: (T) -> Unit = {}
    ) {
        when (this) {
            is DataState.Loading -> {
                if (showLoading) {
                    progressUtil.showProgress()
                }
            }

            is DataState.Success -> {
                if (showLoading) {
                    progressUtil.hideProgress()
                }
                if (showSuccessToast) requireContext().showToast(
                    (data as BaseResponse<*>).msg,
                    ToastType.SUCCESS
                )
                onSuccess(this.data)
            }

            is DataState.Error -> {
                if (showLoading) progressUtil.hideProgress()
                handleError(throwable = throwable, showErrorToast = showErrorToast)
            }
            DataState.Idle -> {
            }
        }
    }

    private fun handleError(throwable: Throwable, showErrorToast: Boolean) {
        throwable.getIsCommonException()?.let {
            requireContext().showToast(getString(it))
        } ?: kotlin.run {
            when (throwable) {
                is NetworkExceptions.AuthorizationException -> {
                    onLogout()
                }

                is NetworkExceptions.CustomException -> {
                    if (showErrorToast) requireContext().showToast(throwable.msg)
                }
            }
        }
    }

    protected fun onLogout() {
        lifecycleScope.launchWhenCreated {
            viewModel.preferenceRepository.onLogout()
            openAuthActivity()
        }
    }

    private fun openAuthActivity() {
        Intent(requireActivity(), AuthActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK).also {
                startActivity(it)
            }
    }
}