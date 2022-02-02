package com.aait.sa.cycles.auth_cycle.login

import androidx.lifecycle.viewModelScope
import com.aait.domain.entities.AuthData
import com.aait.domain.entities.BaseResponse
import com.aait.domain.usecases.LoginUseCase
import com.aait.domain.util.DataState
import com.aait.sa.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : BaseViewModel() {

    private val _loginResponse =
        MutableStateFlow<DataState<BaseResponse<AuthData>>>(DataState.Idle)
    val loginResponse: MutableStateFlow<DataState<BaseResponse<AuthData>>>
        get() = _loginResponse

    fun login(phone: String, password: String, deviceId: String) {
        viewModelScope.launch {
            loginUseCase(
                phone = phone,
                password = password,
                deviceId = deviceId
            ).onEach {
                _loginResponse.value = it
            }.launchIn(viewModelScope)
        }
    }
}