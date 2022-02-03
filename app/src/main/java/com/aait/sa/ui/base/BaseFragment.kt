package com.aait.sa.ui.base

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.aait.sa.ui.base.util.Inflate
import com.aait.sa.ui.base.util.NetworkExtensionsActions
import com.aait.sa.ui.cycles.auth_cycle.activity.AuthActivity
import com.aait.utils.common.*
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment(),
    NetworkExtensionsActions {

    @Inject
    lateinit var progressUtil: ProgressUtil

    @Inject
    lateinit var networkHelper: NetworkHelper

    abstract val viewModel: BaseViewModel

    private var _binding: VB? = null
    val binding get() = _binding!!
    private var isInitialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startObserver()
    }

    open fun startObserver() {
        viewModel.onClearedObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (_binding == null) {
            _binding = inflate.invoke(inflater, container, false)
            afterCreateView()
        } else {
            isInitialized = true
        }

        return binding.root
    }

    open fun afterCreateView() {

    }

    override fun onStart() {
        super.onStart()
        if (!isInitialized) {
            runAfterCreateView()
        }

        afterInitializedBinding()

        backDefaultKey()
    }

    open fun runAfterCreateView() {

    }

    open fun afterInitializedBinding() {

    }

    private fun backDefaultKey() {
        requireView().isFocusableInTouchMode = true
        requireView().requestFocus()

        requireView().setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        onBackPressed()
                        return true
                    }
                }
                return false
            }
        })
    }

    open fun onBackPressed() {
        activity?.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun resetField(target: Any, fieldName: String) {
        val field = target.javaClass.getDeclaredField(fieldName)

        with(field) {
            isAccessible = true
            set(target, null)
        }
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