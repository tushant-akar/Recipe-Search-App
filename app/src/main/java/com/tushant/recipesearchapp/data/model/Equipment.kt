package com.tushant.recipesearchapp.data.model


import com.google.gson.annotations.SerializedName

data class Equipment(
    @SerializedName("equipment")
    val equipment: List<Equipment?>?
) {
    data class Equipment(
        @SerializedName("image")
        val image: String?,
        @SerializedName("name")
        val name: String?
    )
}