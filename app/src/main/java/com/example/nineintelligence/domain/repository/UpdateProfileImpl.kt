package com.example.nineintelligence.domain.repository

import com.example.nineintelligence.BuildConfig
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.data.network.apiservice.UpdateProfile
import com.example.nineintelligence.domain.models.UpdateProfileModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.parameter
import io.ktor.client.request.put

class UpdateProfileImpl(
    private val client: HttpClient,
    private val prefs: AuthPrefs
) : UpdateProfile {
    override suspend fun updateProfile(
        userName: String,
        userEmail: String,
        password: String,
        phone: String,
        address: String,
        profilePic: String,
        gender: String
    ): UpdateProfileModel? {
        return client.put("${BuildConfig.BASE_URL}users/profile/edit") {
            parameter("user_name", userName)
            parameter("user_email", userEmail)
            parameter("password", password)
            parameter("phone", phone)
            parameter("address", address)
            parameter("pp_link", profilePic)
            parameter("gender", gender)
            bearerAuth(prefs.readToken() ?: "")
        }.body()
    }
}