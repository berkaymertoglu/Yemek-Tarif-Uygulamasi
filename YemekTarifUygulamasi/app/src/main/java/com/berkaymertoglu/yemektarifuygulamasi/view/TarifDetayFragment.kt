package com.berkaymertoglu.yemektarifuygulamasi.view

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.berkaymertoglu.yemektarifuygulamasi.databinding.FragmentTarifDetayBinding
import com.berkaymertoglu.yemektarifuygulamasi.model.Tarif
import com.berkaymertoglu.yemektarifuygulamasi.roomdb.TarifDAO
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.room.Room
import com.berkaymertoglu.yemektarifuygulamasi.roomdb.TarifDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class TarifDetayFragment : Fragment() {

    private var _binding: FragmentTarifDetayBinding? = null
    private val binding get() = _binding!!
    private lateinit var tarifDao: TarifDAO
    private lateinit var db: TarifDatabase
    private val mDisposable = CompositeDisposable()
    private var tarifId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTarifDetayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Veritabanını burada başlat
        db = Room.databaseBuilder(
            requireContext(), // Fragment bağlamı doğru
            TarifDatabase::class.java,
            "TarifDatabase"
        ).addMigrations(TarifDatabase.MIGRATION_1_2) // Migration eklendi
            .build()

        tarifDao = db.tarifDao()

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.let { bundle ->
            tarifId = bundle.getInt("id")
            val isim = bundle.getString("isim")
            val malzeme = bundle.getString("malzeme")
            val gorselByteArray = bundle.getByteArray("gorsel")

            System.out.println("Bundle'dan gelen tarifId: $tarifId")

            binding.favorilerdenCikarButton.setOnClickListener { favorilerdenCikar() }
            binding.tarifBaslikTextView.text = isim
            binding.malzemelerTextView.text = "Malzemeler:"
            binding.malzemelerTextView.setTextColor(Color.parseColor("#FF0000"))
            binding.malzemelerContentTextView.text = malzeme
            binding.malzemelerContentTextView.setTextColor(Color.parseColor("#007BFF"))

            // Görselin set edilmesi
            gorselByteArray?.let {
                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                binding.tarifResimImageView.setImageBitmap(bitmap)
            }
        }
    }


    fun favorilerdenCikar() {
        Log.d("Favori", "Favorilerden çıkar fonksiyonu çalıştı. ID: $tarifId")
        if (tarifId > 0) {
            mDisposable.add(
                tarifDao.favorilerdenCikar(tarifId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d("Favori", "Başarıyla favorilerden çıkarıldı!")
                        Toast.makeText(requireContext(), "Tarif favorilerden çıkarıldı!", Toast.LENGTH_SHORT).show()
                    }, { error ->
                        Log.e("Favori", "Hata oluştu: ${error.localizedMessage}")
                        Toast.makeText(requireContext(), "Bir hata oluştu!", Toast.LENGTH_SHORT).show()
                    })
            )
        } else {
            Log.e("Favori", "Geçersiz tarifId: $tarifId")
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}