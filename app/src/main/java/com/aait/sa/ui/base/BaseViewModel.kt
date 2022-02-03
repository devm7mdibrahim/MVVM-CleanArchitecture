package com.aait.sa.ui.base

import androidx.lifecycle.ViewModel
import com.aait.domain.repository.PreferenceRepository
import javax.inject.Inject

open class BaseViewModel : ViewModel() {

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    open fun onClearedObserver() {
        super.onCleared()
    }
}