package com.devm7mdibrahim.presentation.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.devm7mdibrahim.domain.entities.BaseResponse
import com.devm7mdibrahim.domain.exceptions.NetworkExceptions
import com.devm7mdibrahim.domain.util.DataState
import com.devm7mdibrahim.presentation.cycles.auth_cycle.activity.AuthActivity
import com.devm7mdibrahim.presentation.utils.getIsCommonException
import com.devm7mdibrahim.utils.common.ProgressUtil
import com.devm7mdibrahim.utils.extensions.ToastType
import com.devm7mdibrahim.utils.extensions.showToast
import javax.inject.Inject

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment() {

    @Inject
    lateinit var progressUtil: ProgressUtil

    abstract val viewModel: BaseViewModel

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startObserver()
    }

    open fun startObserver() {}

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

    override fun onDestroy() {
        super.onDestroy()
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
            is NetworkExceptions.AuthorizationException -> {
                onLogout()
            }

            is NetworkExceptions.NeedActiveException -> {
                requireContext().showToast(throwable.msg, ToastType.WARNING)
            }

            is NetworkExceptions.CustomException -> {
                requireContext().showToast(throwable.msg)
            }

            else -> {
                requireContext().showToast(getString(throwable.getIsCommonException()))
            }
        }
    }

    private fun onLogout() {
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