package com.example.mentalhealth.ui.main.chatbot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.mentalhealth.ui.viewmodel.MentalHealthViewModel

data class ChatMessage(
    val message: String,
    val isFromUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatbotScreen(viewModel: MentalHealthViewModel) {
    val messages = remember { mutableStateListOf<ChatMessage>() }
    var userInput by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    
    // Initial greeting
    LaunchedEffect(Unit) {
        messages.add(
            ChatMessage(
                message = "Olá! Eu sou Yoru, seu terapeuta virtual. Como você está se sentindo hoje?",
                isFromUser = false
            )
        )
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Chat header
        Text(
            text = "Yoru - Terapeuta Virtual",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Messages
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { message ->
                ChatBubble(message = message)
            }
        }
        
        // Scroll to bottom when new message is added
        LaunchedEffect(messages.size) {
            if (messages.isNotEmpty()) {
                listState.animateScrollToItem(messages.size - 1)
            }
        }
        
        // Input field
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = userInput,
                onValueChange = { userInput = it },
                placeholder = { Text("Digite sua mensagem...") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                shape = RoundedCornerShape(24.dp)
            )
            
            IconButton(
                onClick = {
                    if (userInput.isNotBlank()) {
                        // Add user message
                        messages.add(ChatMessage(message = userInput, isFromUser = true))
                        
                        // Store input and clear field
                        val input = userInput
                        userInput = ""
                        
                        // Simulate bot thinking and respond
                        coroutineScope.launch {
                            delay(1000) // Simulate thinking
                            messages.add(
                                ChatMessage(
                                    message = generateResponse(input),
                                    isFromUser = false
                                )
                            )
                        }
                    }
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    Icons.Default.Send,
                    contentDescription = "Enviar",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (message.isFromUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 300.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (message.isFromUser) 16.dp else 0.dp,
                        bottomEnd = if (message.isFromUser) 0.dp else 16.dp
                    )
                )
                .background(
                    if (message.isFromUser) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.secondaryContainer
                )
                .padding(12.dp)
        ) {
            Text(
                text = message.message,
                color = if (message.isFromUser) MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

fun generateResponse(userInput: String): String {
    // Simple response generation logic
    // In a real app, this would be more sophisticated or use an actual AI model
    return when {
        userInput.contains("sad", ignoreCase = true) -> 
            "Desculpe ouvir que você está se sentindo triste. Gostaria de falar sobre o que está causando esses sentimentos?"
        
        userInput.contains("anxious", ignoreCase = true) || userInput.contains("anxiety", ignoreCase = true) -> 
            "Ansiedade pode ser desafiadora. Você já tentou algum exercício de respiração? Respirar lentamente e profundamente pode ajudar a calmar o seu sistema nervoso."
        
        userInput.contains("happy", ignoreCase = true) || userInput.contains("good", ignoreCase = true) -> 
            "Estou feliz em ouvir que você está se sentindo bem! O que tem contribuído para sua boa disposição?"
        
        userInput.contains("tired", ignoreCase = true) || userInput.contains("exhausted", ignoreCase = true) -> 
            "Estar cansado pode afetar o seu estado mental. Você tem estado dormindo o suficiente? É importante priorizar o sono."
        
        userInput.contains("help", ignoreCase = true) -> 
            "Estou aqui para ajudar. Você poderia me contar mais especificamente o que você está lutando?"
        
        userInput.length < 10 -> 
            "Gostaria de entender mais. Você poderia expandir sobre o que você está experimentando?"
        
        else -> 
            "Obrigado por compartilhar. Como isso te faz sentir? Lembre-se, estou aqui para ouvir e te apoiar."
    }
}
