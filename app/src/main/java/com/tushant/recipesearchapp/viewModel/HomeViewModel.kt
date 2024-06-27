package com.tushant.recipesearchapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tushant.recipesearchapp.data.model.RandomRecipes
import com.tushant.recipesearchapp.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val recipeRepository: RecipeRepository) :
    ViewModel() {

    private val _popularRecipes = MutableLiveData<RandomRecipes>()
    val popularRecipes: LiveData<RandomRecipes> = _popularRecipes

    private val _allRecipes = MutableLiveData<RandomRecipes>()
    val allRecipes: LiveData<RandomRecipes> = _allRecipes

    fun fetchPopularRecipes(number: Int) {
        viewModelScope.launch {
            try {
                val response = recipeRepository.getRandomRecipes(number)
                _popularRecipes.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("HomeViewModel", "Error fetching popular recipes: ${e.message}")
            }
        }
    }

    fun fetchAllRecipes(number: Int) {
        viewModelScope.launch {
            try {
                val response = recipeRepository.getRandomRecipes(number)
                _allRecipes.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("HomeViewModel", "Error fetching all recipes: ${e.message}")
            }
        }
    }
}