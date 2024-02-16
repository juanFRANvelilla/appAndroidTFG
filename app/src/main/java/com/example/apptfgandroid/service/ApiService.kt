package com.example.tfgapp.service

import com.example.apptfgandroid.models.CreateUserDTO
import com.example.apptfgandroid.models.LoginRequestDTO
import com.example.apptfgandroid.models.PhoneValidationDTO
import com.example.apptfgandroid.models.ContactRequestDTO
import com.example.apptfgandroid.models.UserDTO
import com.example.tfgapp.models.ServerResponseDTO
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body data: LoginRequestDTO): ServerResponseDTO

    @POST("api/confirmPhone")
    suspend fun confirmPhone(@Body data: PhoneValidationDTO): Map<String, Any>

    @POST("api/validatePhone")
    suspend fun validatePhone(@Body data: CreateUserDTO): Map<String, Any>

    @GET("api2/showContacts")
    suspend fun showContacts(): Set<UserDTO>

    @POST("api2/requestContact")
    suspend fun sendContactRequest(@Body data: ContactRequestDTO): Map<String, Any>

    @POST("api2/acceptRequestContact")
    suspend fun acceptContactRequest(@Body data: ContactRequestDTO): Map<String, Any>

    @GET("api2/showRequestContact")
    suspend fun showContactRequest(): Set<UserDTO>

}



object RetrofitService{
    fun contactsCallsJwt(token: String): ApiService{
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(TokenInterceptor(token))
            .build()

        return Retrofit.Builder()
            .baseUrl("http://192.168.0.128:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }
}

class TokenInterceptor(private val jwtToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $jwtToken")
            .build()
        return chain.proceed(request)
    }
}
