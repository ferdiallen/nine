package com.example.nineintelligence.domain.use_case.tryout_use_case

import com.example.nineintelligence.data.network.apiservice.Discussion
import com.example.nineintelligence.domain.models.DiscussModel
import com.example.nineintelligence.domain.util.DiscussionType
import com.example.nineintelligence.domain.util.Resource
import java.io.IOException

class DiscussionUseCase(
    private val discussion: Discussion
) {
    suspend fun getPembahasan(slugname: String,discussionType: DiscussionType): Resource<List<DiscussModel>> {
        return try {
            val res = discussion.discussionResult(slugname,discussionType)
            Resource.Success(res)
        } catch (e: Exception) {
            Resource.Error(e.message)
        } catch (e: IOException) {
            Resource.Error(e.message)
        }
    }
}