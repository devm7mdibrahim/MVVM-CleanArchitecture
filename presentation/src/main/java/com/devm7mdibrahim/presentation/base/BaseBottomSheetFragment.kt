package com.devm7mdibrahim.presentation.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.devm7mdibrahim.presentation.cycles.auth_cycle.activity.AuthActivity
import com.devm7mdibrahim.presentation.utils.Inflate
import com.devm7mdibrahim.presentation.utils.NetworkExtensionsActions
import com.devm7mdibrahim.utils.common.NetworkHelper
import com.devm7mdibrahim.utils.common.ProgressUtil
import com.devm7mdibrahim.utils.extensions.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

abstract class BaseBottomSheetFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) :
    BottomSheetDialogFragment(), NetworkExtensionsActions {

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
            onCreateView()
        }
        afterCreateView()

        return binding.root
    }

    open fun onCreateView() {

    }

    open fun afterCreateView() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, com.devm7mdibrahim.utils.R.style.bottomSheetDialogStyle)
    }

    override fun onLoad(showLoading: Boolean) {
        progressUtil.statusProgress(showLoading)
    }

    override fun onCommonError(exceptionMsgId: Int) {
        requireContext().showToast(getString(exceptionMsgId))
    }

    override fun onShowSuccessToast(msg: String?) {
        requireContext().showToast(msg, ToastType.SUCCESS)
    }

    override fun onFail(msg: String?) {
        requireContext().showToast(msg)
    }

    override fun authorizationNeedActive(msg: String) {
        requireContext().showToast(msg, ToastType.WARNING)
    }

    override fun authorizationFail() {
        requireContext().openAccountDeletedDialog {
            onLogout()
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