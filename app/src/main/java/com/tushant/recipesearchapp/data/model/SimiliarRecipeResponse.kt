package com.tushant.recipesearchapp.data.model


import com.google.gson.annotations.SerializedName

class SimiliarRecipeResponse : ArrayList<SimiliarRecipeResponse.SimiliarRecipeResponseItem>() {
    data class SimiliarRecipeResponseItem(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("imageType")
        val imageType: String?,
        @SerializedName("readyInMinutes")
        val readyInMinutes: Int?,
        @SerializedName("servings")
        val servings: Int?,
        @SerializedName("sourceUrl")
        val sourceUrl: String?,
        @SerializedName("title")
        val title: String?
    )
}