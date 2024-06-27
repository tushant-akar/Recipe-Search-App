package com.tushant.recipesearchapp.ui.adapter.favouriteFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tushant.recipesearchapp.R
import com.tushant.recipesearchapp.data.model.FavouriteRecipe

class FavouriteRecipeAdapter(
    private var recipes: List<FavouriteRecipe>
) : RecyclerView.Adapter<FavouriteRecipeAdapter.FavouriteRecipeViewHolder>() {

    inner class FavouriteRecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeItemImage = itemView.findViewById<ImageView>(R.id.recipeItemImg)
        val recipeItemName = itemView.findViewById<TextView>(R.id.recipeItemName)
        val recipeItemTime = itemView.findViewById<TextView>(R.id.recipeItemTime)
    }

    private var onItemClickListener: ((FavouriteRecipe) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavouriteRecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return FavouriteRecipeViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: FavouriteRecipeViewHolder,
        position: Int
    ) {
        val recipe = recipes[position]
        holder.recipeItemName.text = recipe.title
        holder.recipeItemTime.text = "${recipe.readyInMinutes} min"
        holder.recipeItemImage.load(recipe.image)

        holder.itemView.setOnClickListener {
            recipe?.let { onItemClickListener?.invoke(it) }
        }
    }

    override fun getItemCount() = recipes.size

    fun setRecipes(recipes: List<FavouriteRecipe>) {
        this.recipes = recipes
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (FavouriteRecipe) -> Unit) {
        onItemClickListener = listener
    }
}
