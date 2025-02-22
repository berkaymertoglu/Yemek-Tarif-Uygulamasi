package com.berkaymertoglu.yemektarifuygulamasi.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.berkaymertoglu.yemektarifuygulamasi.databinding.ItemRecipeBinding
import com.berkaymertoglu.yemektarifuygulamasi.model.Recipe
import com.berkaymertoglu.yemektarifuygulamasi.R
import android.graphics.Color

class RecipeAdapter(
    private val recipeList: List<Recipe>,
    private val fragment: Fragment // Fragment referansı alıyoruz
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(private val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            Glide.with(itemView.context)
                .load(recipe.image)
                .into(binding.recipeImage)

            binding.recipeTitle.text = recipe.title
            binding.recipeTitle.setTextColor(Color.YELLOW)

            // Yemek öğesine tıklanınca RecipeDetailFragment'e yönlendir
            itemView.setOnClickListener {
                val bundle = Bundle().apply {
                    putInt("RECIPE_ID", recipe.id)  // ID'yi bundle'a ekliyoruz
                }

                // NavController ile geçiş yapıyoruz
                itemView.findNavController().navigate(R.id.action_yemekTarifleriFragment_to_recipeDetailFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipeList[position])
    }

    override fun getItemCount(): Int = recipeList.size
}
