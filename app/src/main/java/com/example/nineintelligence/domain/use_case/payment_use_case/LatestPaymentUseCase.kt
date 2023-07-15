package com.example.nineintelligence.domain.use_case.payment_use_case

import com.example.nineintelligence.data.network.apiservice.LatestTransaction
import com.example.nineintelligence.domain.models.LatestPaymentResponse
import com.example.nineintelligence.domain.util.Resource
import java.io.IOException

class LatestPaymentUseCase(
    private val latestTransaction: LatestTransaction
) {
    suspend fun getLatestTransaction(): Resource<LatestPaymentResponse> {
        return try {
            val res = latestTransaction.latestTransaction()
            Resource.Success(res)
        } catch (e: Exception) {
            Resource.Error(e.message)
        } catch (e: IOException) {
            Resource.Error(e.message)
        }
    }
}