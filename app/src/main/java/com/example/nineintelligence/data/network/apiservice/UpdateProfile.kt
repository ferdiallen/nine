package com.example.nineintelligence.data.network.apiservice

import com.example.nineintelligence.domain.models.UpdateProfileModel

interface UpdateProfile {
    suspend fun updateProfile(
        userId:String,
        userName: String,
        userEmail: String,
        phone: String,
        address: String,
        profilePic: String,
        gender: String
    ): String
}