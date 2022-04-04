package com.devm7mdibrahim.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.devm7mdibrahim.domain.entities.BaseResponse
import com.devm7mdibrahim.domain.exceptions.NetworkExceptions
import com.devm7mdibrahim.domain.util.DataState
import com.devm7mdibrahim.presentation.utils.getIsCommonException
import com.devm7mdibrahim.utils.common.ProgressUtil
import com.devm7mdibrahim.utils.extensions.ToastType
import com.devm7mdibrahim.utils.extensions.showToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

abstract class BaseBottomSheetFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) :
    BottomSheetDialogFragment() {

    @Inject
    lateinit var progressUtil: ProgressUtil

    abstract val viewModel: BaseViewModel

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, com.devm7mdibrahim.utils.R.style.bottomSheetDialogStyle)
        startObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (_binding == null) {
            _binding = inflate.invoke(inflater, container, false)
            onCreateBinding()
        }

        handleClicks()
        return binding.root
    }

    open fun onCreateBinding() {}

    open fun handleClicks() {}

    open fun startObserver() {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun <T> DataState<T>.applyCommonSideEffects(
        showLoading: Boolean = true,
        showSuccessToast: Boolean = true,
        onSuccess: (T) -> Unit = {}
    ) {
        when (this) {
            is DataState.Loading -> {
                if (showLoading) progressUtil.showProgress()
            }

            is DataState.Success -> {
                if (showSuccessToast) requireContext().showToast(
                    (data as BaseResponse<*>).msg,
                    ToastType.SUCCESS
                )
                onSuccess(this.data)
            }

            is DataState.Error -> {
                progressUtil.hideProgress()
                handleError(throwable)
            }

            DataState.Idle -> {
                progressUtil.hideProgress()
            }
        }
    }

    private fun handleError(throwable: Throwable) {
        when (throwable) {
            is NetworkExceptions.CustomException -> {
                requireContext().showToast(throwable.msg)
            }

            else -> {
                requireContext().showToast(getString(throwable.getIsCommonException()))
            }
        }
    }
}