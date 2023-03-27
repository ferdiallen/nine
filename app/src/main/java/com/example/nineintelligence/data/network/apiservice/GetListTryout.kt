package com.example.nineintelligence.data.network.apiservice

import com.example.nineintelligence.domain.models.TryoutDataModel

interface GetListTryout {
    suspend fun getListTryout() : List<TryoutDataModel>
}