package com.example.nineintelligence.domain.use_case.payment_use_case

import com.example.nineintelligence.data.network.apiservice.UpdatePayment
import com.example.nineintelligence.domain.util.Resource
import java.io.IOException

class UpdatePaymentUseCase(
    private val updatePayment: UpdatePayment
) {
    suspend fun updatePayment(orderId: String): Resource<String?> {
        return try {
            Resource.Success(updatePayment.updatePayment(orderId))
        }catch (e:Exception){
            println(e.cause)
            Resource.Error(e.message)
        }catch (e:IOException){
            println(e.cause)
            Resource.Error(e.message)
        }
    }
}