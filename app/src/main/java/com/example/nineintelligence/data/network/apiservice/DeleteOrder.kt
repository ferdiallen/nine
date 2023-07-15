package com.example.nineintelligence.data.network.apiservice

interface DeleteOrder {
    suspend fun deleteOrder(orderId:String) : Boolean
}