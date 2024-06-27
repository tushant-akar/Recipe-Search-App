package com.tushant.recipesearchapp.data.remote

import javax.inject.Inject

class APIHelper @Inject constructor(private val recipeAPI: RecipeAPI) {
    suspend fun searchRecipes(query: String) = recipeAPI.searchRecipes(query)
    suspend fun getRandomRecipes(number: Int) = recipeAPI.getRandomRecipes(number)
    suspend fun getRecipeInformation(id: Int) = recipeAPI.getRecipeInformation(id)
    suspend fun getSimilarRecipes(id: Int) = recipeAPI.getSimilarRecipes(id)
    suspend fun getRecipeEquipment(id: Int) = recipeAPI.getRecipeEquipment(id)
    suspend fun getRecipeIngredients(id: Int) = recipeAPI.getRecipeIngredients(id)
    suspend fun getRecipeNutrition(id: Int) = recipeAPI.getRecipeNutrition(id)
}