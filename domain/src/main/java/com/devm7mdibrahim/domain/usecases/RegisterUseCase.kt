package com.devm7mdibrahim.domain.usecases

import com.devm7mdibrahim.domain.entities.AuthData
import com.devm7mdibrahim.domain.entities.BaseResponse
import com.devm7mdibrahim.domain.exceptions.ValidationException
import com.devm7mdibrahim.domain.repository.AuthRepository
import com.devm7mdibrahim.domain.util.CommonValidation.isValidName
import com.devm7mdibrahim.domain.util.CommonValidation.isValidPassword
import com.devm7mdibrahim.domain.util.CommonValidation.isValidPhone
import com.devm7mdibrahim.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        name: String,
        phone: String,
        email: String,
        password: String,
        avatar: String?
    ): Flow<DataState<BaseResponse<AuthData>>> = flow {
        when {
            !name.isValidName() -> emit(DataState.Error(ValidationException.InValidNameException))
            !phone.isValidPhone() -> emit(DataState.Error(ValidationException.InValidPhoneException))
            !email.isValidPhone() -> emit(DataState.Error(ValidationException.InValidEmailAddressException))
            !password.isValidPassword() -> emit(DataState.Error(ValidationException.InValidPasswordException))
            else -> emitAll(
                authRepository.register(
                    name = name,
                    phone = phone,
                    email = email,
                    password = password,
                    avatar = avatar
                )
            )
        }
    }
}