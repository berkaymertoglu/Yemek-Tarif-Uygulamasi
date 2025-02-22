package com.berkaymertoglu.yemektarifuygulamasi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.berkaymertoglu.yemektarifuygulamasi.adapter.TarifAdapter
import com.berkaymertoglu.yemektarifuygulamasi.databinding.FragmentFavorilerBinding
import com.berkaymertoglu.yemektarifuygulamasi.model.Tarif
import com.berkaymertoglu.yemektarifuygulamasi.roomdb.TarifDAO
import com.berkaymertoglu.yemektarifuygulamasi.roomdb.TarifDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class FavorilerFragment : Fragment() {

    private var _binding: FragmentFavorilerBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: TarifDatabase
    private lateinit var tarifDao: TarifDAO
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
        _binding = FragmentFavorilerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favorilerRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        verileriGetir()
    }

    private fun verileriGetir() {
        mDisposable.add(
            tarifDao.getFavoriTarifler()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError)
        )
    }

    private fun handleResponse(tarifler: List<Tarif>) {
        val adapter = TarifAdapter(tarifler)
        binding.favorilerRecyclerView.adapter = adapter
    }

    private fun handleError(throwable: Throwable) {
        throwable.printStackTrace()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mDisposable.clear()
    }
}

