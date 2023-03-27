package com.example.nineintelligence.domain.repository

import com.example.nineintelligence.BuildConfig
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.data.network.apiservice.UpdateProfile
import com.example.nineintelligence.domain.models.UpdateProfileModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

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
            contentType(ContentType.Application.Json)
            setBody(
                UpdateProfileModel(
                    userName, userEmail, password, phone, address, profilePic, gender
                )
            )
            bearerAuth(prefs.readToken() ?: "")
        }.body()
    }
}