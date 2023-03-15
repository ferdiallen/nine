package com.example.nineintelligence.domain.repository

import com.example.nineintelligence.BuildConfig
import com.example.nineintelligence.data.network.apiservice.LoginUser
import com.example.nineintelligence.domain.models.LoginModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.http.Parameters

class LoginUserImpl(
    private val client: HttpClient
) : LoginUser {
    override suspend fun loginUser(name: String, password: String): LoginModel {
        return client.submitForm(url = "${BuildConfig.BASE_URL}users/login",
            formParameters = Parameters.build {
            append("username", name)
            append("password", password)
        }) {}.body()
    }
}