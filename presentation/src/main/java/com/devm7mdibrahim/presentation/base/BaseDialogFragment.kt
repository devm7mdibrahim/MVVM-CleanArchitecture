package com.devm7mdibrahim.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.devm7mdibrahim.domain.entities.BaseResponse
import com.devm7mdibrahim.domain.exceptions.NetworkExceptions
import com.devm7mdibrahim.domain.util.DataState
import com.devm7mdibrahim.presentation.utils.getIsCommonException
import com.devm7mdibrahim.utils.common.ProgressUtil
import com.devm7mdibrahim.utils.extensions.ToastType
import com.devm7mdibrahim.utils.extensions.showToast
import javax.inject.Inject

abstract class BaseDialogFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) :
    DialogFragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    @Inject
    lateinit var progressUtil: ProgressUtil

    abstract val viewModel: BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (_binding == null) {
            _binding = inflate.invoke(inflater, container, false)
            onCreateView()
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startObserver()
    }

    open fun startObserver() {

    }

    open fun onCreateView() {

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}