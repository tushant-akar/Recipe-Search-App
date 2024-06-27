package com.tushant.recipesearchapp.data.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchRecipeResponse(
    @SerializedName("results")
    var results: List<Result?>?,
): Serializable {
    data class Result(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("imageType")
        val imageType: String?,
        @SerializedName("title")
        val title: String?
    ): Serializable
}