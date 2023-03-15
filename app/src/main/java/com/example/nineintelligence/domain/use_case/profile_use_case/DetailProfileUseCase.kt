package com.example.nineintelligence.domain.use_case.profile_use_case

import com.example.nineintelligence.data.network.apiservice.DetailUser
import com.example.nineintelligence.domain.models.UserProfileModel
import com.example.nineintelligence.domain.util.Resource
import java.io.IOException

class DetailProfileUseCase(private val user: DetailUser) {
    suspend fun getDetailUser(): Resource<UserProfileModel?> {
        return try {
            Resource.Loading(null)
            val res = user.getDetailUser()
            Resource.Success(res)
        } catch (e: Exception) {
            Resource.Error(e.message)
        } catch (e: IOException) {
            Resource.Error(e.message)
        }
    }
}