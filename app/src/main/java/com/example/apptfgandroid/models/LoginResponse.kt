package com.example.tfgapp.models

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("message")
    var message:String,
    @SerializedName("token")
    var token:String,
    @SerializedName("username")
    var username:String,
)




