package com.example.nineintelligence.domain.use_case.login_use_case

import com.example.nineintelligence.data.network.apiservice.LoginUser
import com.example.nineintelligence.domain.util.Resource
import java.io.IOException

class LoginUseCase(
    private val api: LoginUser
) {
    suspend fun getUserAuth(name: String, password: String) = try {
            val res = api.loginUser(name, password)
            Resource.Success(res)
        } catch (e: Exception) {
            Resource.Error(e.message)
        } catch (e: IOException) {
            Resource.Error(e.message)
        }

}