package com.example.mychecklist.model


import com.google.gson.annotations.SerializedName

data class RegisterAuth(
    @SerializedName("email")
    val email: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("username")
    val username: String?
)