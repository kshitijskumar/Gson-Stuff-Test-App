package com.example.gsonstuffapp

import com.google.gson.InstanceCreator
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type

data class AllDefaultsResponse(
    @SerializedName("someName") val name: String = "unknown",
    @SerializedName("someNumber") val number: Int = 5
)

data class OnlyNumberDefaultResponse(
    @SerializedName("someName") val name: String,
    @SerializedName("someNumber") val number: Int = 5
)

data class InstanceCreatorResponse(
    @SerializedName("someName") val name: String,
    @SerializedName("someNumber") val number: Int
)

data class BackingFieldResponse(
    @SerializedName("someName") val name: String,
    @SerializedName("someNumber") val number: Int
) {
    val actualNumber: Int get() {
        return if (number == 0) return 10 else number
    }
}

class InstanceCreatorResponseAdapter : InstanceCreator<InstanceCreatorResponse> {
    override fun createInstance(type: Type?): InstanceCreatorResponse {
        return InstanceCreatorResponse("unknown", 10)
    }
}