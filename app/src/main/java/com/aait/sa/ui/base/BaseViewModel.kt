package com.aait.sa.ui.base

import androidx.lifecycle.ViewModel
import com.aait.domain.entities.BaseResponse
import com.aait.domain.repository.PreferenceRepository
import com.aait.domain.util.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

open class BaseViewModel : ViewModel() {

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    var _generalResponse =
        MutableStateFlow<DataState<BaseResponse<Any>>>(DataState.Idle)
    val generalResponse: MutableStateFlow<DataState<BaseResponse<Any>>>
        get() = _generalResponse

    open fun onClearedObserver() {
        super.onCleared()
        _generalResponse =
            MutableStateFlow(DataState.Idle)
    }
}