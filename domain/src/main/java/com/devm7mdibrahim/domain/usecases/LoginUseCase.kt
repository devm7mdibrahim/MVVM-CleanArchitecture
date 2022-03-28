package com.devm7mdibrahim.domain.usecases

import com.devm7mdibrahim.domain.entities.AuthData
import com.devm7mdibrahim.domain.entities.BaseResponse
import com.devm7mdibrahim.domain.exceptions.ValidationException
import com.devm7mdibrahim.domain.repository.AuthRepository
import com.devm7mdibrahim.domain.util.CommonValidation.isValidPassword
import com.devm7mdibrahim.domain.util.CommonValidation.isValidPhone
import com.devm7mdibrahim.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        phone: String,
        password: String,
        deviceId: String
    ): Flow<DataState<BaseResponse<AuthData>>> = flow {
        when {
            !phone.isValidPhone() -> emit(DataState.Error(ValidationException.InValidPhoneException))
            !password.isValidPassword() -> emit(DataState.Error(ValidationException.InValidPasswordException))
            else -> emitAll(
                authRepository.login(
                    phone = phone,
                    password = password,
                    deviceId = deviceId
                )
            )
        }
    }
}