package com.example.nineintelligence.domain.repository

import com.example.nineintelligence.BuildConfig
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.data.network.apiservice.DetailUser
import com.example.nineintelligence.domain.models.UserProfileModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get

class DetailUserImpl(
    private val client: HttpClient,
    private val auth: AuthPrefs
) : DetailUser {
    override suspend fun getDetailUser(): UserProfileModel? {
        return client.get("${BuildConfig.BASE_URL}users/profile") {
            bearerAuth(auth.readToken() ?: return@get)
        }.body()
    }
}