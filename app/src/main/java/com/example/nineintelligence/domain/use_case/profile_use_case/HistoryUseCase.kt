package com.example.nineintelligence.domain.use_case.profile_use_case

import com.example.nineintelligence.data.network.apiservice.History
import com.example.nineintelligence.domain.models.HistoryModel
import com.example.nineintelligence.domain.util.Resource
import java.io.IOException

class HistoryUseCase(
    private val history: History
) {
    suspend fun getHistory(): Resource<List<HistoryModel>> {
        return try {
            val res = history.getHistory()
            Resource.Success(res)
        } catch (e: Exception) {
            Resource.Error(e.message)
        } catch (e: IOException) {
            Resource.Error(e.message)
        }
    }
}