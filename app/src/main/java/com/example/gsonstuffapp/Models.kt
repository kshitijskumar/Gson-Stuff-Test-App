package com.example.gsonstuffapp

import com.google.gson.annotations.SerializedName

data class AllDefaultsResponse(
    @SerializedName("someName") val name: String = "unknown",
    @SerializedName("someNumber") val number: Int = 5
)

data class OnlyNumberDefaultResponse(
    @SerializedName("someName") val name: String,
    @SerializedName("someNumber") val number: Int = 5
)