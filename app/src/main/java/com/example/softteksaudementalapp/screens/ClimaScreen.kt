package com.example.softteksaudementalapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.softteksaudementalapp.model.RespostaClima
import com.example.softteksaudementalapp.model.RespostaCargaEAlerta
import kotlin.math.roundToInt

@Composable
fun ClimaScreen(
    onSalvarClima: (RespostaClima) -> Unit,
    onSalvarCargaEAlerta: (RespostaCargaEAlerta) -> Unit
) {
    val scrollState = rememberScrollState()

    val perguntasRelacionamento = listOf(
        "Como está o seu relacionamento com seu chefe numa escala de 1 a 5?",
        "Como está o seu relacionamento com seus colegas de trabalho numa escala de 1 a 5?",
        "Sinto que sou tratado(a) com respeito pelos meus colegas de trabalho.",
        "Consigo me relacionar de forma saudável e colaborativa com minha equipe.",
        "Tenho liberdade para expressar minhas opiniões sem medo de retaliações.",
        "Me sinto acolhido(a) a parte do time onde trabalho.",
        "Sinto que existe espírito de cooperação entre os colaboradores."
    )
    val respostasRelacionamento = remember { mutableStateListOf(*Array(7) { "3" }) }

    val perguntasComunicacao = listOf(
        "Recebo orientações claras e objetivas sobre minhas atividades e responsabilidades.",
        "Sinto que posso me comunicar abertamente com minha liderança.",
        "As informações importantes circulam de forma eficiente dentro da empresa.",
        "Tenho clareza sobre as metas e os resultados esperados de mim."
    )
    val respostasComunicacao = remember { mutableStateListOf(*Array(4) { "3" }) }

    val perguntasLideranca = listOf(
        "Minha liderança demonstra interesse pelo meu bem-estar no trabalho.",
        "Minha liderança está disponível para me ouvir quando necessário.",
        "Me sinto confortável para reportar problemas ou dificuldades ao meu líder.",
        "Minha liderança reconhece minhas entregas e esforços.",
        "Existe confiança e transparência na relação com minha liderança."
    )
    val respostasLideranca = remember { mutableStateListOf(*Array(5) { "3" }) }

    var cargaTrabalho by remember { mutableStateOf("") }
    var qualidadeVida by remember { mutableStateOf("") }
    var trabalhaForaHorario by remember { mutableStateOf("") }
    var insoniaOuCansaco by remember { mutableStateOf("") }
    var produtividade by remember { mutableStateOf("") }
    var enviado by remember { mutableStateOf(false) }

    val opcoesNota = listOf("1", "2", "3", "4", "5")
    val opcoesCarga = listOf("Muito Leve", "Leve", "Média", "Alta", "Muito Alta")
    val opcoesFrequencia = listOf("Não", "Raramente", "Às vezes", "Frequentemente", "Sempre")
    val opcoesAlerta = listOf("Nunca", "Raramente", "Às vezes", "Frequentemente", "Sempre")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Diagnóstico de Clima Organizacional", style = MaterialTheme.typography.titleLarge)

        Text("Relacionamento", style = MaterialTheme.typography.titleMedium)
        perguntasRelacionamento.forEachIndexed { i, pergunta ->
            RadioPergunta(pergunta, respostasRelacionamento[i], opcoesNota) {
                respostasRelacionamento[i] = it
            }
        }

        Divider()

        Text("Comunicação", style = MaterialTheme.typography.titleMedium)
        perguntasComunicacao.forEachIndexed { i, pergunta ->
            RadioPergunta(pergunta, respostasComunicacao[i], opcoesNota) {
                respostasComunicacao[i] = it
            }
        }

        Divider()

        Text("Liderança", style = MaterialTheme.typography.titleMedium)
        perguntasLideranca.forEachIndexed { i, pergunta ->
            RadioPergunta(pergunta, respostasLideranca[i], opcoesNota) {
                respostasLideranca[i] = it
            }
        }

        Divider()

        Text("Fatores de Carga de Trabalho", style = MaterialTheme.typography.titleMedium)
        DropdownPergunta("Como você avalia sua carga de trabalho?", cargaTrabalho, opcoesCarga) { cargaTrabalho = it }
        DropdownPergunta("Sua carga de trabalho afeta sua qualidade de vida?", qualidadeVida, opcoesFrequencia) { qualidadeVida = it }
        DropdownPergunta("Você trabalha além do seu horário regular?", trabalhaForaHorario, opcoesFrequencia) { trabalhaForaHorario = it }

        Divider()

        Text("Sinais de Alerta", style = MaterialTheme.typography.titleMedium)
        DropdownPergunta("Você tem apresentado sintomas como insônia, irritabilidade ou cansaço extremo?", insoniaOuCansaco, opcoesAlerta) { insoniaOuCansaco = it }
        DropdownPergunta("Você sente que sua saúde mental prejudica sua produtividade no trabalho?", produtividade, opcoesAlerta) { produtividade = it }

        Button(
            onClick = {
                val mediaRelacionamento = respostasRelacionamento.map { it.toInt() }.average().roundToInt()
                val mediaComunicacao = respostasComunicacao.map { it.toInt() }.average().roundToInt()
                val mediaLideranca = respostasLideranca.map { it.toInt() }.average().roundToInt()

                onSalvarClima(
                    RespostaClima(
                        relacionamento = mediaRelacionamento,
                        comunicacao = mediaComunicacao,
                        lideranca = mediaLideranca
                    )
                )
                onSalvarCargaEAlerta(
                    RespostaCargaEAlerta(
                        cargaTrabalho = cargaTrabalho,
                        qualidadeVida = qualidadeVida,
                        trabalhaForaHorario = trabalhaForaHorario,
                        insoniaOuCansaco = insoniaOuCansaco,
                        produtividade = produtividade
                    )
                )
                enviado = true
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = cargaTrabalho.isNotBlank() && qualidadeVida.isNotBlank() &&
                    trabalhaForaHorario.isNotBlank() && insoniaOuCansaco.isNotBlank() && produtividade.isNotBlank()
        ) {
            Text("Salvar Respostas")
        }

        if (enviado) {
            Text("Dados salvos com sucesso!", color = MaterialTheme.colorScheme.primary)
        }

        Spacer(modifier = Modifier.height(64.dp))
    }
}

@Composable
fun RadioPergunta(pergunta: String, selecionado: String, opcoes: List<String>, onSelecionar: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(pergunta, style = MaterialTheme.typography.bodyLarge)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            opcoes.forEach { opcao ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    RadioButton(
                        selected = selecionado == opcao,
                        onClick = { onSelecionar(opcao) }
                    )
                    Text(opcao, style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownPergunta(pergunta: String, selecionado: String, opcoes: List<String>, onSelecionar: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(pergunta, style = MaterialTheme.typography.bodyLarge)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selecionado,
                onValueChange = {},
                readOnly = true,
                label = { Text("Selecione") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                opcoes.forEach { opcao ->
                    DropdownMenuItem(
                        text = { Text(opcao) },
                        onClick = {
                            onSelecionar(opcao)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
