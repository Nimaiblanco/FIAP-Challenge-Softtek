package com.example.softteksaudementalapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.softteksaudementalapp.model.*

@Database(
    entities = [
        RegistroHumor::class,
        RespostaAvaliacao::class,
        RespostaClima::class,
        RespostaCargaEAlerta::class // ✅ nova entidade adicionada
    ],
    version = 8
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun humorDao(): HumorDao
    abstract fun climaDao(): ClimaDao
    abstract fun cargaEAlertaDao(): CargaAlertaDao // ✅ novo DAO adicionado

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "softtek.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
