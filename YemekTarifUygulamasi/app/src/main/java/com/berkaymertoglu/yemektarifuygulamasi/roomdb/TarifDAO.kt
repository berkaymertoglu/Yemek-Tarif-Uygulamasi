package com.berkaymertoglu.yemektarifuygulamasi.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.berkaymertoglu.yemektarifuygulamasi.model.Tarif
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable


@Dao
interface TarifDAO {

    @Query("SELECT * FROM Tarif WHERE favori = 0")
    fun getAll() : Flowable<List<Tarif>>

    @Query("SELECT * FROM Tarif WHERE id = :id")
    fun findById(id : Int) : Flowable<Tarif>

    @Query("SELECT * FROM Tarif WHERE favori = 1")
    fun getFavoriTarifler(): Flowable<List<Tarif>>

    @Query("UPDATE Tarif SET favori = 1 WHERE id = :tarifId")
    fun favoriYap(tarifId: Int) : Completable

    @Query("UPDATE Tarif SET favori = 0 WHERE id = :tarifId")
    fun favorilerdenCikar(tarifId: Int) : Completable

    @Insert
    fun insert(tarif: Tarif) : Completable

    @Delete
    fun delete(tarif: Tarif) : Completable

}

