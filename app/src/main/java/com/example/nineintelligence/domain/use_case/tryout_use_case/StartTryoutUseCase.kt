package com.example.nineintelligence.domain.use_case.tryout_use_case

import com.example.nineintelligence.data.network.apiservice.StartTryout
import com.example.nineintelligence.domain.models.StartTryoutResponse
import com.example.nineintelligence.domain.util.Resource
import okio.IOException

class StartTryoutUseCase(
    private val tryout: StartTryout
) {
    suspend fun startTryout(slugname: String): Resource<StartTryoutResponse> {
        return try {
            val res = tryout.startTryout(slugname)
            Resource.Success(res)
        }/* catch (e: Exception) {
            println(e.message)
            Resource.Error(e.message)
        }*/ catch (e: IOException) {
            println(e.message)
            Resource.Error(e.message)
        }
    }
}