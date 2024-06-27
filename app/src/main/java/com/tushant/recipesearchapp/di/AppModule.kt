package com.tushant.recipesearchapp.di

import android.content.Context
import androidx.room.Room
import com.tushant.recipesearchapp.data.local.FavouriteRecipeDao
import com.tushant.recipesearchapp.data.local.FavouriteRecipeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): FavouriteRecipeDatabase {
        return Room.databaseBuilder(
            appContext,
            FavouriteRecipeDatabase::class.java,
            "favourite_recipe_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideFavouriteRecipeDao(db: FavouriteRecipeDatabase): FavouriteRecipeDao {
        return db.favouriteRecipeDao()
    }
}