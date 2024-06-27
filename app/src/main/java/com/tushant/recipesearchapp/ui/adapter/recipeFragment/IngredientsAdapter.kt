package com.tushant.recipesearchapp.ui.adapter.recipeFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tushant.recipesearchapp.R
import com.tushant.recipesearchapp.data.model.Ingredients

class IngredientsAdapter(private var ingredients: Ingredients) :
    RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    inner class IngredientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ingredientName = itemView.findViewById<TextView>(R.id.ingredientName)
        val ingredientImage = itemView.findViewById<ImageView>(R.id.ingredient_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.ingredients_item, parent, false)
        return IngredientsViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val ingredient = ingredients.ingredients?.get(position)
        holder.ingredientName.text = ingredient?.name
        holder.ingredientImage.load(ingredient?.image)
    }

    override fun getItemCount() = ingredients.ingredients?.size ?: 0

    fun setIngredients(ingredients: Ingredients) {
        this.ingredients = ingredients
        notifyDataSetChanged()
    }
}