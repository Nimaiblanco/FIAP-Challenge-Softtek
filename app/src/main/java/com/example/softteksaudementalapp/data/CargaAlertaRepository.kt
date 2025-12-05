package com.example.softteksaudementalapp.data

import com.example.softteksaudementalapp.model.RespostaCargaEAlerta
import kotlinx.coroutines.flow.Flow

class CargaAlertaRepository(private val dao: CargaAlertaDao) {

    fun listar(): Flow<List<RespostaCargaEAlerta>> = dao.listar()

    suspend fun salvar(resposta: RespostaCargaEAlerta) {
        dao.inserir(resposta)
    }
}

