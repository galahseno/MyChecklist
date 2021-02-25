package com.example.mychecklist.data

import com.example.mychecklist.model.LoginAuth
import com.example.mychecklist.model.checklist.ChecklistItem
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    suspend fun registerAuth(@Body requestBody: RequestBody) : Response<ResponseBody>

    @POST("login")
    suspend fun loginAuth(@Body requestBody: RequestBody) : Response<LoginAuth>

    @GET("checklist")
    suspend fun getChecklist(@Header("Authorization") token: String) : List<ChecklistItem>

}