package com.tushant.recipesearchapp.data.model


import com.google.gson.annotations.SerializedName

data class Nutrition(
    @SerializedName("caloricBreakdown")
    val caloricBreakdown: CaloricBreakdown?,
    @SerializedName("flavonoids")
    val flavonoids: List<Flavonoid?>?,
    @SerializedName("ingredients")
    val ingredients: List<Ingredient?>?,
    @SerializedName("nutrients")
    val nutrients: List<Nutrient?>?,
    @SerializedName("properties")
    val properties: List<Property?>?,
    @SerializedName("weightPerServing")
    val weightPerServing: WeightPerServing?
) {
    data class CaloricBreakdown(
        @SerializedName("percentCarbs")
        val percentCarbs: Double?,
        @SerializedName("percentFat")
        val percentFat: Double?,
        @SerializedName("percentProtein")
        val percentProtein: Double?
    )

    data class Flavonoid(
        @SerializedName("amount")
        val amount: Double?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("unit")
        val unit: String?
    )

    data class Ingredient(
        @SerializedName("amount")
        val amount: Double?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("nutrients")
        val nutrients: List<Nutrient?>?,
        @SerializedName("unit")
        val unit: String?
    ) {
        data class Nutrient(
            @SerializedName("amount")
            val amount: Double?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("percentOfDailyNeeds")
            val percentOfDailyNeeds: Double?,
            @SerializedName("unit")
            val unit: String?
        )
    }

    data class Nutrient(
        @SerializedName("amount")
        val amount: Double?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("percentOfDailyNeeds")
        val percentOfDailyNeeds: Double?,
        @SerializedName("unit")
        val unit: String?
    )

    data class Property(
        @SerializedName("amount")
        val amount: Double?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("unit")
        val unit: String?
    )

    data class WeightPerServing(
        @SerializedName("amount")
        val amount: Int?,
        @SerializedName("unit")
        val unit: String?
    )
}