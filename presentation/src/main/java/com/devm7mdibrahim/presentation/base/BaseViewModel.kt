package com.devm7mdibrahim.presentation.base

import androidx.lifecycle.ViewModel
import com.devm7mdibrahim.domain.repository.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(): ViewModel() {

    @Inject
    lateinit var preferenceRepository: PreferenceRepository
}