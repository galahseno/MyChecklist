package com.example.mychecklist.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("token")
    val token: String?
)