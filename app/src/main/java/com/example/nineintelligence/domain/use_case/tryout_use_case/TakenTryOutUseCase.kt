package com.example.nineintelligence.domain.use_case.tryout_use_case

import com.example.nineintelligence.data.network.apiservice.TakenTryOut
import com.example.nineintelligence.domain.models.TakenTryOutModel
import com.example.nineintelligence.domain.util.Resource
import java.io.IOException

class TakenTryOutUseCase(
    private val takenTryOut: TakenTryOut
) {
    suspend fun getListTakenTryOut(): Resource<List<TakenTryOutModel>> {
        return try {
            val data = takenTryOut.getTryOutTakenList()
            Resource.Success(data)
        } catch (e: Exception) {
            Resource.Error(e.message)
        } catch (e: IOException) {
            Resource.Error(e.message)
        }
    }
}