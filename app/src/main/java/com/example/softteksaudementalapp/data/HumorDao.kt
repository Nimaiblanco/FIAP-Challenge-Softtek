package com.example.softteksaudementalapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.softteksaudementalapp.model.RegistroHumor
import kotlinx.coroutines.flow.Flow

@Dao
interface HumorDao {

    @Insert
    suspend fun inserir(registro: RegistroHumor)

    @Query("SELECT * FROM RegistroHumor ORDER BY dataHora DESC")
    fun getTodos(): Flow<List<RegistroHumor>>
}
