package com.berkaymertoglu.yemektarifuygulamasi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.berkaymertoglu.yemektarifuygulamasi.databinding.FragmentRecipeDetailBinding
import com.berkaymertoglu.yemektarifuygulamasi.model.RecipeDetailResponse
import com.berkaymertoglu.yemektarifuygulamasi.model.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeDetailFragment : Fragment() {

    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fragment'a gönderilen argümanlardan yemek ID'sini alıyoruz
        val recipeId = arguments?.getInt("RECIPE_ID") ?: return
        // API'den yemek detaylarını alıyoruz
        getRecipeDetails(recipeId)
    }

    private fun getRecipeDetails(recipeId: Int) {
        RetrofitClient.apiService.getRecipeDetails(recipeId, "dd00e7da68f94fafbefa99fe2e45ee1e").enqueue(object :
            Callback<RecipeDetailResponse> {
            override fun onResponse(call: Call<RecipeDetailResponse>, response: Response<RecipeDetailResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { recipe ->
                        // Başlık, resim ve tarif bilgilerini bağlıyoruz
                        binding.recipeTitle.text = recipe.title
                        Glide.with(requireContext()).load(recipe.image).into(binding.recipeImage)
                        binding.recipeInstructions.text = recipe.instructions
                    }
                }
            }

            override fun onFailure(call: Call<RecipeDetailResponse>, t: Throwable) {
                // Hata durumunda kullanıcıya bilgi veriyoruz
                binding.recipeInstructions.text = "Hata: ${t.message}"
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
