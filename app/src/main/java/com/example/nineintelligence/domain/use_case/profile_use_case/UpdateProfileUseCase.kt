package com.example.nineintelligence.domain.use_case.profile_use_case

import com.example.nineintelligence.data.network.apiservice.UpdateProfile
import com.example.nineintelligence.domain.models.UpdateProfileModel
import com.example.nineintelligence.domain.util.Resource
import java.io.IOException

class UpdateProfileUseCase(
    private val updateProfile: UpdateProfile
) {
    suspend fun updateProfile(
        userId: String,
        username: String,
        userEmail: String,
        phone: String,
        address: String,
        profilePic: String,
        gender: String
    ): Resource<String> {
        return try {
            Resource.Loading(null)
            val data = updateProfile.updateProfile(
                userId,
                username,
                userEmail,
                phone,
                address,
                profilePic,
                gender
            )
            Resource.Success(data)
        } catch (e: Exception) {
            println(e.message)
            Resource.Error(e.message)
        } catch (e: IOException) {
            Resource.Error(e.message)
        }
    }
}