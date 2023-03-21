package com.example.nineintelligence.data.network.apiservice

import com.example.nineintelligence.domain.models.UpdateProfileModel

interface UpdateProfile {
    suspend fun updateProfile(
        userName: String,
        userEmail: String,
        password: String,
        phone: String,
        address: String,
        profilePic: String,
        gender: String
    ): UpdateProfileModel?
}