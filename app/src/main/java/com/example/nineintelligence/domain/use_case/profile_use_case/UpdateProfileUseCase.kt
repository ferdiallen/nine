package com.example.nineintelligence.domain.use_case.profile_use_case

import com.example.nineintelligence.data.network.apiservice.UpdateProfile
import com.example.nineintelligence.domain.models.UpdateProfileModel
import com.example.nineintelligence.domain.util.Resource
import java.io.IOException

class UpdateProfileUseCase(
    private val updateProfile: UpdateProfile
) {
    suspend fun updateProfile(
        username: String,
        userEmail: String,
        password: String,
        phone: String,
        address: String,
        profilePic: String,
        gender: String
    ): Resource<UpdateProfileModel> {
        return try {
            Resource.Loading(null)
            val data = updateProfile.updateProfile(
                username,
                userEmail,
                password,
                phone,
                address,
                profilePic,
                gender
            )
            Resource.Success(data)
        } catch (e: Exception) {
            println(e.message)
            println(e.localizedMessage)
            Resource.Error(e.message)
        } catch (e: IOException) {
            println(e.message)
            Resource.Error(e.message)
        }
    }
}