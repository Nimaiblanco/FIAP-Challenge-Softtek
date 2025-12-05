package com.example.softteksaudementalapp.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ClimaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(resposta: RespostaClima)

    @Query("SELECT * FROM respostas_clima ORDER BY dataHora DESC")
    fun listar(): Flow<List<RespostaClima>>
}
