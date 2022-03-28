package com.devm7mdibrahim.presentation.cycles.auth_cycle.fragment.login

import androidx.lifecycle.viewModelScope
import com.devm7mdibrahim.domain.entities.AuthData
import com.devm7mdibrahim.domain.entities.BaseResponse
import com.devm7mdibrahim.domain.usecases.LoginUseCase
import com.devm7mdibrahim.domain.util.DataState
import com.devm7mdibrahim.presentation.base.BaseViewModel
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