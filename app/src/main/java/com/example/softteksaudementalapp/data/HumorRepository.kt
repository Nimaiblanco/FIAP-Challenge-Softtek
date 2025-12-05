package com.example.softteksaudementalapp.data

import com.example.softteksaudementalapp.model.RegistroHumor
import kotlinx.coroutines.flow.Flow

class HumorRepository(private val humorDao: HumorDao) {

    val todos: Flow<List<RegistroHumor>> = humorDao.getTodos()

    suspend fun inserir(registro: RegistroHumor) {
        humorDao.inserir(registro)
    }
}
