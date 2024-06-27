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

class AllRecipesAdapter(private var recipes: RandomRecipes) :
    RecyclerView.Adapter<AllRecipesAdapter.AllRecipeViewHolder>() {

    inner class AllRecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeItemImage: ImageView = itemView.findViewById(R.id.recipeItemImg)
        val recipeItemName: TextView = itemView.findViewById(R.id.recipeItemName)
        val recipeItemTime: TextView = itemView.findViewById(R.id.recipeItemTime)
    }

    private var onItemClickListener: ((RandomRecipes.Recipe) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllRecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return AllRecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllRecipeViewHolder, position: Int) {
        val recipe = recipes.recipes?.get(position)
        holder.recipeItemName.text = recipe?.title ?: ""
        holder.recipeItemTime.text = "${recipe?.readyInMinutes} min"
        holder.recipeItemImage.load(recipe?.image)

        holder.itemView.setOnClickListener {
            recipe?.let { onItemClickListener?.invoke(it) }
        }
    }

    override fun getItemCount() = recipes.recipes?.size ?: 0

    fun setAllRecipes(recipes: RandomRecipes) {
        this.recipes = recipes
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (RandomRecipes.Recipe) -> Unit) {
        onItemClickListener = listener
    }
}
