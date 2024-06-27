package com.tushant.recipesearchapp.data.remote

import com.tushant.recipesearchapp.data.model.Equipment
import com.tushant.recipesearchapp.data.model.Ingredients
import com.tushant.recipesearchapp.data.model.Nutrition
import com.tushant.recipesearchapp.data.model.RandomRecipes
import com.tushant.recipesearchapp.data.model.SearchRecipeResponse
import com.tushant.recipesearchapp.data.model.SimiliarRecipeResponse
import com.tushant.recipesearchapp.util.Constants.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeAPI {
    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): SearchRecipeResponse

    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ): RandomRecipes

    @GET("recipes/{id}/information")
    suspend fun getRecipeInformation(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ): RandomRecipes.Recipe

    @GET("recipes/{id}/similar")
    suspend fun getSimilarRecipes(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ): SimiliarRecipeResponse

    @GET("recipes/{id}/equipmentWidget.json")
    suspend fun getRecipeEquipment(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ): Equipment

    @GET("recipes/{id}/ingredientWidget.json")
    suspend fun getRecipeIngredients(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ): Ingredients

    @GET("recipes/{id}/nutritionWidget.json")
    suspend fun getRecipeNutrition(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ): List<Nutrition>
}