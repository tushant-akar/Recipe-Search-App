package com.tushant.recipesearchapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tushant.recipesearchapp.data.model.FavouriteRecipe
import com.tushant.recipesearchapp.data.repository.FavouriteRecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val favouriteRecipeRepository: FavouriteRecipeRepository) :
    ViewModel() {

    private val _favouriteRecipes = MutableLiveData<List<FavouriteRecipe>>()
    val favouriteRecipes: LiveData<List<FavouriteRecipe>> = _favouriteRecipes

    fun fetchFavouriteRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = favouriteRecipeRepository.getAllFavouriteRecipes()
                _favouriteRecipes.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("FavouriteViewModel", "Error fetching favourite recipes: ${e.message}")
            }
        }
    }
}