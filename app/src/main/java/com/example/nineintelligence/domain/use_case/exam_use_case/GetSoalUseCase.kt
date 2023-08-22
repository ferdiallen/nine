package com.example.nineintelligence.domain.use_case.exam_use_case

import com.example.nineintelligence.data.network.apiservice.GetSoal
import com.example.nineintelligence.domain.models.GetSoalModel
import com.example.nineintelligence.domain.util.DiscussionType
import com.example.nineintelligence.domain.util.Resource
import java.io.IOException

class GetSoalUseCase(
    private val getSoal: GetSoal
) {
    suspend fun getSoal(slugname: String,type:DiscussionType): Resource<List<GetSoalModel>> {
        return try {
            val result = getSoal.getSoal(slugname,type)
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message)
        } catch (e: IOException) {
            Resource.Error(e.message)
        }
    }
}