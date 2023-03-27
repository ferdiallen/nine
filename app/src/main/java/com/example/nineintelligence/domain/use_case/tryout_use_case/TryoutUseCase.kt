package com.example.nineintelligence.domain.use_case.tryout_use_case

import com.example.nineintelligence.data.network.apiservice.GetListTryout
import com.example.nineintelligence.domain.models.TryoutDataModel
import com.example.nineintelligence.domain.util.Resource
import io.ktor.utils.io.errors.IOException

class TryoutUseCase(
    private val tryout: GetListTryout
) {
    suspend fun getListTryOut(): Resource<List<TryoutDataModel>> {
        return try {
            Resource.Success(tryout.getListTryout())
        } catch (e: Exception) {
            Resource.Error(e.message)
        } catch (e: IOException) {
            Resource.Error(e.message)
        }
    }
}