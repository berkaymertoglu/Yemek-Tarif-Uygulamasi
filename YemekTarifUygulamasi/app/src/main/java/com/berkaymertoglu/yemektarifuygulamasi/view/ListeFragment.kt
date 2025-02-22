package com.berkaymertoglu.yemektarifuygulamasi.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.berkaymertoglu.yemektarifuygulamasi.adapter.TarifAdapter
import com.berkaymertoglu.yemektarifuygulamasi.databinding.FragmentListeBinding
import com.berkaymertoglu.yemektarifuygulamasi.model.Tarif
import com.berkaymertoglu.yemektarifuygulamasi.roomdb.TarifDAO
import com.berkaymertoglu.yemektarifuygulamasi.roomdb.TarifDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class ListeFragment : Fragment() {

    private var _binding: FragmentListeBinding? = null
    private val binding get() = _binding!!

    private lateinit var db : TarifDatabase
    private lateinit var tarifDao : TarifDAO
    private val mDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(
            requireContext(),
            TarifDatabase::class.java,
            "TarifDatabase"
        ).addMigrations(TarifDatabase.MIGRATION_1_2)
            .build()

        tarifDao = db.tarifDao()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingActionButton.setOnClickListener {yeniEkle(it)}
        binding.secondButton.setOnClickListener { favorilerSayfasiniAc(it) }
        binding.yemekTarifleriButton.setOnClickListener { yemekTarifleriSayfasiniAc(it) }
        binding.yakindakiMarketlerButton.setOnClickListener {yakindakiMarketlerSayfasiniAc(it)}
        binding.tarifRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        verileriAl()
    }

    private fun verileriAl() {
        println("Verileri Çekmeye Başladım.")
        mDisposable.add(
            tarifDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse)
        )
    }

    private fun handleResponse(tarifler: List<Tarif>) {
       /* tarifler.forEach {
            println(it.isim)
            println(it.malzeme)
         } */
        val adapter = TarifAdapter(tarifler)
        binding.tarifRecyclerView.adapter = adapter
        tarifler.forEach { println(it) }

    }

    fun yeniEkle(view : View) {
        val action = ListeFragmentDirections.actionListeFragmentToTarifFragment(bilgi = "yeni", id = 0)
        Navigation.findNavController(view).navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mDisposable.clear()
    }


    fun favorilerSayfasiniAc(view: View) {
        val action = ListeFragmentDirections.actionListeFragmentToFavorilerFragment()
        Navigation.findNavController(view).navigate(action)
    }

    fun yemekTarifleriSayfasiniAc(view: View) {
        val action = ListeFragmentDirections.actionListeFragmentToYemekTarifleriFragment()
        Navigation.findNavController(view).navigate(action)
    }

    fun yakindakiMarketlerSayfasiniAc(view: View) {
        val action = ListeFragmentDirections.actionListeFragmentToYakindakiMarketlerFragment()
        Navigation.findNavController(view).navigate(action)
    }
}