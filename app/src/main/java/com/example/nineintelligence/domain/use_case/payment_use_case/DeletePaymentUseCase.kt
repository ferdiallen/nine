package com.example.nineintelligence.domain.use_case.payment_use_case

import com.example.nineintelligence.data.network.apiservice.DeleteOrder
import com.example.nineintelligence.domain.util.Resource
import java.io.IOException

class DeletePaymentUseCase(private val deleteOrder: DeleteOrder) {
    suspend fun deleteOrder(orderId: String): Resource<Boolean> {
        return try {
            Resource.Success(deleteOrder.deleteOrder(orderId))
        }catch (e:Exception){
            Resource.Error(e.message)
        }catch (e:IOException){
            Resource.Error(e.message)
        }
    }
}