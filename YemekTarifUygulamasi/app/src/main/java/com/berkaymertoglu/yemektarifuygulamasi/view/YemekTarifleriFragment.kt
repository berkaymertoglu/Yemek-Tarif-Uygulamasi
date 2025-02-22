package com.berkaymertoglu.yemektarifuygulamasi.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.berkaymertoglu.yemektarifuygulamasi.R
import com.berkaymertoglu.yemektarifuygulamasi.adapter.RecipeAdapter
import com.berkaymertoglu.yemektarifuygulamasi.model.Recipe
import com.berkaymertoglu.yemektarifuygulamasi.model.RecipeResponse
import com.berkaymertoglu.yemektarifuygulamasi.model.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class YemekTarifleriFragment : Fragment(R.layout.fragment_yemek_tarifleri) {

    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var recipeList: MutableList<Recipe>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView'ı alıyoruz
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        // RecyclerView'ı LinearLayoutManager ile kuruyoruz
        recyclerView.layoutManager = LinearLayoutManager(context)

        // RecipeAdapter'ı oluşturuyoruz
        recipeList = mutableListOf()
        recipeAdapter = RecipeAdapter(recipeList, this)
        recyclerView.adapter = recipeAdapter

        // API'den veri çekiyoruz
        fetchRecipes()
    }

    private fun fetchRecipes() {
        val apiKey = "dd00e7da68f94fafbefa99fe2e45ee1e"  // API anahtarınızı buraya girin

        RetrofitClient.apiService.getRecipes(apiKey, 10)  // Burada 10 tarif çekiliyor
            .enqueue(object : Callback<RecipeResponse> {
                override fun onResponse(call: Call<RecipeResponse>, response: Response<RecipeResponse>) {
                    if (response.isSuccessful) {
                        val recipes = response.body()?.results
                        recipes?.let {
                            recipeList.clear()
                            recipeList.addAll(it)
                            recipeAdapter.notifyDataSetChanged()  // Adapter'ı güncelliyoruz
                        }
                    }
                }

                override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                    // Hata durumunu burada yönetebilirsiniz
                }
            })
    }
}
