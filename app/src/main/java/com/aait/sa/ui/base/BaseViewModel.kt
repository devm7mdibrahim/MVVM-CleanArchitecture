package com.aait.sa.ui.base

import androidx.lifecycle.ViewModel
import com.aait.domain.repository.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(): ViewModel() {

    @Inject
    lateinit var preferenceRepository: PreferenceRepository
}