package com.aait.sa.base

import androidx.lifecycle.ViewModel
import com.aait.domain.repository.PreferenceRepository
import javax.inject.Inject

open class BaseViewModel : ViewModel() {
    @Inject
    lateinit var preferenceRepository: PreferenceRepository
}