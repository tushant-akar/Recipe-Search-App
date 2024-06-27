package com.tushant.recipesearchapp.ui.adapter.homeFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tushant.recipesearchapp.R
import com.tushant.recipesearchapp.data.model.RandomRecipes

class PopularRecipeAdapter(private var recipes: RandomRecipes) :
    RecyclerView.Adapter<PopularRecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val popularItemImage = itemView.findViewById<ImageView>(R.id.popularItemImg)
        val popularItemName = itemView.findViewById<TextView>(R.id.popularItemName)
        val popularItemTime = itemView.findViewById<TextView>(R.id.popularItemTime)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.popular_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val popularRecipe = recipes.recipes?.get(position)
        holder.popularItemName.text = popularRecipe?.title
        holder.popularItemTime.text = "Ready in ${popularRecipe?.readyInMinutes} min"
        holder.popularItemImage.load(popularRecipe?.image)

    }

    override fun getItemCount() = recipes.recipes?.size ?: 0

    fun setRecipes(recipes: RandomRecipes) {
        this.recipes = recipes
        notifyDataSetChanged()
    }

}