package com.example.mychecklist.model


import com.google.gson.annotations.SerializedName

data class LoginAuth(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("errorMessage")
    val errorMessage: Any?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("statusCode")
    val statusCode: Int?
)