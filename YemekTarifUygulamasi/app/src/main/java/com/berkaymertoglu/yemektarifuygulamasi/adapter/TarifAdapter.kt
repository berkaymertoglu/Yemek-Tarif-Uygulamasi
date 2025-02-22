package com.berkaymertoglu.yemektarifuygulamasi.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.berkaymertoglu.yemektarifuygulamasi.databinding.RecyclerRowBinding
import com.berkaymertoglu.yemektarifuygulamasi.model.Tarif
import com.berkaymertoglu.yemektarifuygulamasi.view.TarifDetayFragment
import com.berkaymertoglu.yemektarifuygulamasi.view.ListeFragmentDirections

class TarifAdapter(val tarifListesi: List<Tarif>) : RecyclerView.Adapter<TarifAdapter.TarifHolder>() {

    class TarifHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarifHolder {
        val recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TarifHolder(recyclerRowBinding)
    }

    override fun getItemCount(): Int {
        return tarifListesi.size
    }

    override fun onBindViewHolder(holder: TarifHolder, position: Int) {
        val tarif = tarifListesi[position]
        holder.binding.recyclerViewTextView.text = tarif.isim

        // Eğer favori ise favori sayfasına git, değilse liste sayfasına git
        holder.itemView.setOnClickListener {
            if (tarif.favori == 1) {
                // Favori sayfası için işlem (favori tarifler)
                val bundle = Bundle()
                bundle.putString("isim", tarif.isim)
                bundle.putString("malzeme", tarif.malzeme)
                bundle.putByteArray("gorsel", tarif.gorsel)
                bundle.putInt("id", tarif.id)

                val fragment = TarifDetayFragment()
                fragment.arguments = bundle

                val fragmentManager = it.context as? androidx.fragment.app.FragmentActivity
                fragmentManager?.supportFragmentManager?.beginTransaction()
                    ?.replace(com.berkaymertoglu.yemektarifuygulamasi.R.id.fragmentContainerView, fragment)
                    ?.addToBackStack(null)
                    ?.commit()
            } else {
                // Liste sayfası için işlem (normal tarifler)
                val action = ListeFragmentDirections.actionListeFragmentToTarifFragment(bilgi = "eski", id = tarif.id)
                Navigation.findNavController(it).navigate(action)
            }
        }
    }
}
