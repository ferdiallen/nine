package com.example.nineintelligence.domain.repository

import coil.network.HttpException
import com.example.nineintelligence.BuildConfig
import com.example.nineintelligence.data.network.apiservice.RegisterUser
import com.example.nineintelligence.domain.models.UserModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import java.io.IOException

class RegisterUserImpl(private val client: HttpClient) : RegisterUser {
    override suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        phoneNumber: String
    ): UserModel? {
        return try {
            client.post("${BuildConfig.BASE_URL}users/register") {
                parameter("user_name", name)
                parameter("user_email", email)
                parameter("password", password)
                parameter("phone", phoneNumber)
                contentType(ContentType.Application.Json)
                setBody(UserModel(name, email, password, phoneNumber))
            }.body()
        } catch (e: Exception) {
            println(e.message)
            null
        } catch (e: IOException) {
            println(e.message)
            null
        } catch (e: HttpException) {
            println(e.message)
            null
        }
    }
}