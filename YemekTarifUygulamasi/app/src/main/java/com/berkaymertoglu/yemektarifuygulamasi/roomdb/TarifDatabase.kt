package com.berkaymertoglu.yemektarifuygulamasi.roomdb

import androidx.core.content.ContentProviderCompat.requireContext
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.berkaymertoglu.yemektarifuygulamasi.model.Tarif

@Database(entities = [Tarif::class], version = 2)
abstract class TarifDatabase : RoomDatabase() {
    abstract fun tarifDao(): TarifDAO

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Tarif ADD COLUMN favori INTEGER NOT NULL DEFAULT 0")
            }
        }
    }
}


