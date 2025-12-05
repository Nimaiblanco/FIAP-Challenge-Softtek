package com.example.softteksaudementalapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.softteksaudementalapp.model.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DashboardScreen(
    avaliacoes: List<RespostaAvaliacao>,
    climas: List<RespostaClima>,
    registros: List<RegistroHumor>,
    cargaAlerta: List<RespostaCargaEAlerta>
) {
    val scrollState = rememberScrollState()

    val contagemPorEmocao = avaliacoes.groupingBy { it.emocao }.eachCount()
    val contagemPorIntensidade = avaliacoes.groupingBy { it.intensidade }.eachCount()

    val ultimaData = avaliacoes.maxByOrNull { it.dataHora }?.dataHora
    val dataFormatada = ultimaData?.let {
        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(it))
    } ?: "N/A"

    val resultado = classificarRiscoGlobal(avaliacoes)

    val climaAtual = climas.maxByOrNull { it.dataHora }
    val mediaClima = climaAtual?.let {
        (it.relacionamento + it.comunicacao + it.lideranca) / 3f
    }
    val classificacaoClima = mediaClima?.let {
        when (it) {
            in 0f..2.4f -> "Atenção"
            in 2.5f..3.4f -> "Zona de Alerta"
            else -> "Ambiente Saudável"
        }
    }

    val maisRecenteCarga = cargaAlerta.maxByOrNull { it.dataHora }
    val respostasCarga = maisRecenteCarga?.let {
        listOf(
            it.cargaTrabalho, it.qualidadeVida, it.trabalhaForaHorario,
            it.insoniaOuCansaco, it.produtividade
        )
    } ?: emptyList()

    val total = respostasCarga.size
    val nivel = respostasCarga.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key ?: "Não informado"
    val percentual = if (total > 0) (respostasCarga.count { it == nivel } * 100) / total else 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Painel de Bem-Estar", style = MaterialTheme.typography.titleLarge)
        Text("Última avaliação registrada: $dataFormatada", style = MaterialTheme.typography.bodyMedium)

        CardWithIcon(
            title = "Resumo Psicológico",
            icon = Icons.Default.Mood,
            backgroundColor = Color(0xFFE3F2FD)
        ) {
            Text("- Emoção mais frequente: ${resultado.emocaoDominante}")
            Text("- Frequência: ${resultado.percentual}%")
            Text("- Nível de Risco: ${resultado.nivel}")
        }

        CardWithIcon(
            title = "Clima Organizacional",
            icon = Icons.Default.Group,
            backgroundColor = Color(0xFFFFF9C4)
        ) {
            Text("- Média: ${String.format("%.2f", mediaClima ?: 0f)}")
            Text("- Classificação: $classificacaoClima")
        }

        CardWithIcon(
            title = "Carga de Trabalho e Sinais de Alerta",
            icon = Icons.Default.Warning,
            backgroundColor = Color(0xFFFFEBEE)
        ) {
            Text("- Nível predominante: $nivel")
            Text("- Percentual: $percentual%")
        }

        CardWithIcon(
            title = "Histórico de Check-ins",
            icon = Icons.Default.History,
            backgroundColor = Color(0xFFD1C4E9)
        ) {
            registros.sortedByDescending { it.dataHora }
                .take(5)
                .forEach {
                    val data = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(it.dataHora))
                    Text("- $data: Emoji: ${it.emoji} | Sentimento: ${it.sentimento}")
                }
        }

        CardWithIcon("Contagem por Emoção", Icons.Default.Favorite, Color(0xFFF3E5F5)) {
            contagemPorEmocao.forEach { (emocao, quantidade) ->
                Text("- $emocao: $quantidade")
            }
        }

        CardWithIcon("Contagem por Intensidade", Icons.Default.FlashOn, Color(0xFFFFF3E0)) {
            contagemPorIntensidade.forEach { (intensidade, quantidade) ->
                Text("- $intensidade: $quantidade")
            }
        }

        Spacer(modifier = Modifier.height(64.dp))
    }
}

@Composable
fun CardWithIcon(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, backgroundColor: Color, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                Text(title, style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}
