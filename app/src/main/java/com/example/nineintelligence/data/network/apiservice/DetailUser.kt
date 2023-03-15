package com.example.nineintelligence.data.network.apiservice

import com.example.nineintelligence.domain.models.UserProfileModel

interface DetailUser {
    suspend fun getDetailUser(): UserProfileModel?
}