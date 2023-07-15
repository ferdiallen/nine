package com.example.nineintelligence.data.network.apiservice

interface UpdatePayment {
    suspend fun updatePayment(orderid: String): String
}