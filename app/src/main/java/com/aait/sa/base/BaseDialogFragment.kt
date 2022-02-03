package com.aait.sa.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

abstract class BaseDialogFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) :
    DialogFragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!
    private var isInitialized = false

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startObserver()
    }

    open fun startObserver() {

    }

    open fun afterCreateView() {

    }

    override fun onStart() {
        super.onStart()
        if (!isInitialized) {
            runAfterCreateView()
        }

        afterInitializedBinding()
    }

    open fun runAfterCreateView() {

    }

    open fun afterInitializedBinding() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}