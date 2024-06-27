package com.tushant.recipesearchapp.di

import com.tushant.recipesearchapp.data.remote.APIHelper
import com.tushant.recipesearchapp.data.remote.RecipeAPI
import com.tushant.recipesearchapp.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRecipeAPI(retrofit: Retrofit): RecipeAPI {
        return retrofit.create(RecipeAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideAPIHelper(recipeAPI: RecipeAPI): APIHelper {
        return APIHelper(recipeAPI)
    }
}