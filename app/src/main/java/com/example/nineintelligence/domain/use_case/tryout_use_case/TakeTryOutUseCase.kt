package com.example.nineintelligence.domain.use_case.tryout_use_case

import com.example.nineintelligence.data.network.apiservice.TakeTryout
import com.example.nineintelligence.domain.models.TakeTryOutModel
import com.example.nineintelligence.domain.util.Resource
import java.io.IOException

class TakeTryOutUseCase(
    private val takeTryout: TakeTryout
) {
    suspend fun takeTryOut(slugName: String): Resource<TakeTryOutModel> {
        return try {
            val data = takeTryout.takeTryOut(slugName)
            Resource.Success(data)
        } catch (e: Exception) {
            println(e.message)
            Resource.Error(e.message)
        } catch (e: IOException) {
            println(e.message)
            Resource.Error(e.message)
        }
    }
}