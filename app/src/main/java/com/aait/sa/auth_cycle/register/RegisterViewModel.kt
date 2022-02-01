package com.aait.sa.auth_cycle.register

import androidx.lifecycle.viewModelScope
import com.aait.domain.entities.AuthData
import com.aait.domain.entities.BaseResponse
import com.aait.domain.usecases.RegisterUseCase
import com.aait.domain.util.DataState
import com.aait.sa.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUseCase) :
    BaseViewModel() {

    private val _registerResponse =
        MutableStateFlow<DataState<BaseResponse<AuthData>>>(DataState.Idle)
    val registerResponse: MutableStateFlow<DataState<BaseResponse<AuthData>>>
        get() = _registerResponse

    fun register(name: String, phone: String, email: String, password: String, avatar: String?) {
        viewModelScope.launch {
            registerUseCase(
                name = name,
                phone = phone,
                email = email,
                password = password,
                avatar = avatar
            ).onEach {
                _registerResponse.value = it
            }.launchIn(viewModelScope)
        }
    }
}