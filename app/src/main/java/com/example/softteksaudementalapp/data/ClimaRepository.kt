package com.example.softteksaudementalapp.data

import com.example.softteksaudementalapp.model.ClimaDao
import com.example.softteksaudementalapp.model.RespostaClima
import kotlinx.coroutines.flow.Flow

class ClimaRepository(private val dao: ClimaDao) {

    val respostas: Flow<List<RespostaClima>> = dao.listar()

    suspend fun salvar(resposta: RespostaClima) {
        dao.inserir(resposta)
    }
}
