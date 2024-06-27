package com.tushant.recipesearchapp.ui.adapter.bottomSheetFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tushant.recipesearchapp.R
import com.tushant.recipesearchapp.data.model.SimiliarRecipeResponse

class SimilarAdapter(private var recipes: SimiliarRecipeResponse) :
    RecyclerView.Adapter<SimilarAdapter.SimilarViewHolder>() {

    inner class SimilarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeItemImage: ImageView = itemView.findViewById(R.id.recipeItemImg)
        val recipeItemName: TextView = itemView.findViewById(R.id.recipeItemName)
        val recipeItemTime: TextView = itemView.findViewById(R.id.recipeItemTime)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SimilarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return SimilarViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimilarViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.recipeItemName.text = recipe.title
        holder.recipeItemTime.text = "Ready in ${recipe.readyInMinutes} min"
        holder.recipeItemImage.load(R.drawable.login_background_image)
    }

    override fun getItemCount() = recipes.size

    fun setSimilarRecipes(recipes: SimiliarRecipeResponse) {
        this.recipes = recipes
        notifyDataSetChanged()
    }
}
