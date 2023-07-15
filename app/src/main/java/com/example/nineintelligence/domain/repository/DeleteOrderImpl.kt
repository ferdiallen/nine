package com.example.nineintelligence.domain.repository

import com.example.nineintelligence.BuildConfig
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.data.network.apiservice.DeleteOrder
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete

class DeleteOrderImpl(
    private val httpClient: HttpClient,
    private val authPrefs: AuthPrefs
) : DeleteOrder {
    override suspend fun deleteOrder(orderId: String): Boolean {
        val res = httpClient.delete("${BuildConfig.BASE_URL}payment/delete/$orderId") {
            bearerAuth(authPrefs.readTokenNonBlocking() ?: return@delete)
        }
        return res.status.value in 200..299
    }
}