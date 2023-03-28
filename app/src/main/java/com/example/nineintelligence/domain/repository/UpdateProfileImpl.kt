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
        userId: String,
        userName: String,
        userEmail: String,
        phone: String,
        address: String,
        profilePic: String,
        gender: String
    ): String {
        return client.put("${BuildConfig.BASE_URL}users/profile/edit") {
            contentType(ContentType.Application.Json)
            setBody(
                UpdateProfileModel(
                    userId,
                    userName, userEmail, phone, address, profilePic, gender
                )
            )
            bearerAuth(prefs.readToken() ?: return@put)
        }.body()
    }
}