package com.example.nineintelligence.data.network.apiservice

import com.example.nineintelligence.domain.models.PaymentItemModel

interface GetItemsPayment {
    suspend fun getListItemsPayment(): List<PaymentItemModel>
}