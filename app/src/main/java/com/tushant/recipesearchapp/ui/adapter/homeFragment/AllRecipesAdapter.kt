package com.tushant.recipesearchapp.ui.adapter.homeFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.tushant.recipesearchapp.R
import com.tushant.recipesearchapp.data.model.RandomRecipes

private const val VIEW_TYPE_RECIPE = 0
private const val VIEW_TYPE_AD = 1
private const val ITEMS_PER_AD = 5 // Show ad after every 5 recipes

class AllRecipesAdapter(
    private val context: Context,
    private var recipes: RandomRecipes
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onItemClickListener: ((RandomRecipes.Recipe) -> Unit)? = null

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeItemImage: ImageView = itemView.findViewById(R.id.recipeItemImg)
        val recipeItemName: TextView = itemView.findViewById(R.id.recipeItemName)
        val recipeItemTime: TextView = itemView.findViewById(R.id.recipeItemTime)

        fun bind(recipe: RandomRecipes.Recipe) {
            recipeItemName.text = recipe.title
            recipeItemTime.text = "Ready in ${recipe.readyInMinutes} min"
            recipeItemImage.load(recipe.image)

            itemView.setOnClickListener {
                onItemClickListener?.invoke(recipe)
            }
        }
    }

    inner class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val adView: AdView = itemView.findViewById(R.id.adView)

        init {
            MobileAds.initialize(context)
            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_RECIPE -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
                RecipeViewHolder(view)
            }

            VIEW_TYPE_AD -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.ad_banner_item, parent, false)
                AdViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_RECIPE -> {
                val recipeHolder = holder as RecipeViewHolder
                val recipe =
                    recipes.recipes?.get(position - position / (ITEMS_PER_AD + 1)) // Adjust position for ads
                recipeHolder.bind(recipe!!)
            }

            VIEW_TYPE_AD -> {
                // AdViewHolder already handles ad loading, nothing needed here
            }
        }
    }

    override fun getItemCount(): Int {
        val recipeCount = recipes.recipes?.size ?: 0
        return recipeCount + (recipeCount / ITEMS_PER_AD)
    }

    override fun getItemViewType(position: Int): Int {
        return if ((position + 1) % (ITEMS_PER_AD + 1) == 0 && position != 0) {
            VIEW_TYPE_AD
        } else {
            VIEW_TYPE_RECIPE
        }
    }

    fun setAllRecipes(recipes: RandomRecipes) {
        this.recipes = recipes
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (RandomRecipes.Recipe) -> Unit) {
        onItemClickListener = listener
    }
}
