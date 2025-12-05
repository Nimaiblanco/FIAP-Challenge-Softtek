package com.example.softteksaudementalapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.softteksaudementalapp.model.RespostaCargaEAlerta
import kotlinx.coroutines.flow.Flow

@Dao
interface CargaAlertaDao {
    @Query("SELECT * FROM respostas_carga_alerta ORDER BY dataHora DESC")
    fun listar(): Flow<List<RespostaCargaEAlerta>>

    @Insert
    suspend fun inserir(resposta: RespostaCargaEAlerta)
}
