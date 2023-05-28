package com.example.nineintelligence.domain.use_case.payment_use_case

import com.example.nineintelligence.data.network.apiservice.GetItemsPayment
import com.example.nineintelligence.domain.models.PaymentItemModel
import com.example.nineintelligence.domain.util.Resource
import java.io.IOException

class GetListPaymentItemsUseCase(
    private val getListItemsPayment: GetItemsPayment
) {
    suspend fun getItemPaymentList(): Resource<List<PaymentItemModel>> {
        return try {
            val res = getListItemsPayment.getListItemsPayment()
            Resource.Success(res)
        } catch (e: Exception) {
            Resource.Error(e.message)
        } catch (e: IOException) {
            Resource.Error(e.message)
        }
    }
}