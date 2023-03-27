package com.example.nineintelligence.domain.repository

import com.example.nineintelligence.BuildConfig
import com.example.nineintelligence.data.network.apiservice.GetListTryout
import com.example.nineintelligence.domain.models.TryoutDataModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ListTryOutImpl(
    private val client: HttpClient
) : GetListTryout {
    override suspend fun getListTryout(): List<TryoutDataModel> {
        return client.get("${BuildConfig.BASE_URL}tryouts").body()
    }
}