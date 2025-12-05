package com.example.softteksaudementalapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.softteksaudementalapp.model.RegistroHumor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckInScreen(onSalvar: (RegistroHumor) -> Unit) {
    var emojiSelecionado by remember { mutableStateOf("") }
    var sentimentoSelecionado by remember { mutableStateOf("") }
    var expandedEmoji by remember { mutableStateOf(false) }
    var expandedSentimento by remember { mutableStateOf(false) }
    var enviado by remember { mutableStateOf(false) }

    val opcoesEmoji = listOf("Triste", "Alegre", "Cansado", "Ansioso", "Medo", "Raiva")
    val opcoesSentimento = listOf("Motivado", "Cansado", "Preocupado", "Estressado", "Animado", "Satisfeito")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Como você está se sentindo hoje?", style = MaterialTheme.typography.titleMedium)

        // Dropdown Emoji
        ExposedDropdownMenuBox(
            expanded = expandedEmoji,
            onExpandedChange = { expandedEmoji = !expandedEmoji }
        ) {
            TextField(
                value = emojiSelecionado,
                onValueChange = {},
                readOnly = true,
                label = { Text("Escolha o seu emoji de hoje") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEmoji)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expandedEmoji,
                onDismissRequest = { expandedEmoji = false }
            ) {
                opcoesEmoji.forEach { emoji ->
                    DropdownMenuItem(
                        text = { Text(emoji) },
                        onClick = {
                            emojiSelecionado = emoji
                            expandedEmoji = false
                        }
                    )
                }
            }
        }

        // Dropdown Sentimento
        ExposedDropdownMenuBox(
            expanded = expandedSentimento,
            onExpandedChange = { expandedSentimento = !expandedSentimento }
        ) {
            TextField(
                value = sentimentoSelecionado,
                onValueChange = {},
                readOnly = true,
                label = { Text("Como você se sente hoje?") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSentimento)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expandedSentimento,
                onDismissRequest = { expandedSentimento = false }
            ) {
                opcoesSentimento.forEach { sentimento ->
                    DropdownMenuItem(
                        text = { Text(sentimento) },
                        onClick = {
                            sentimentoSelecionado = sentimento
                            expandedSentimento = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                if (emojiSelecionado.isNotBlank() && sentimentoSelecionado.isNotBlank()) {
                    onSalvar(RegistroHumor(emoji = emojiSelecionado, sentimento = sentimentoSelecionado))
                    enviado = true
                    emojiSelecionado = ""
                    sentimentoSelecionado = ""
                }
            },
            enabled = emojiSelecionado.isNotBlank() && sentimentoSelecionado.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar")
        }

        if (enviado) {
            Text("Check-in registrado com sucesso!", color = MaterialTheme.colorScheme.primary)
        }
    }
}
