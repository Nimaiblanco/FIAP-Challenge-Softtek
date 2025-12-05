package com.example.softteksaudementalapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ApoioScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Saúde Mental Softtek", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text("Recursos de Apoio Psicossocial", style = MaterialTheme.typography.titleMedium)

        listOf(
            "Canal de escuta" to "Procure o RH ou equipe de apoio sempre que sentir necessidade de conversar.",
            "Orientações" to "Busque manter equilíbrio entre trabalho e descanso. Cuide da sua saúde mental.",
            "Terapia" to "A empresa oferece acesso a sessões com psicólogos credenciados. Informe-se com o RH.",
            "Bem-estar" to "Práticas como meditação, mindfulness e pausas ativas ajudam no seu dia a dia.",
            "Atividades" to "Participe dos grupos de apoio e atividades promovidas pela empresa."
        ).forEach { (titulo, texto) ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text(titulo, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                    Text(texto)
                }
            }
        }

        Spacer(modifier = Modifier.height(64.dp)) // Espaço inferior para navegação
    }
}
