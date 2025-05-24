package com.example.mentalhealth.ui.main.mood

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mentalhealth.data.model.MoodEntry
import com.example.mentalhealth.ui.viewmodel.MentalHealthViewModel
import java.text.SimpleDateFormat
import java.util.*

enum class Mood(val icon: ImageVector, val label: String, val color: Color, val value: Int) {
    HAPPY(Icons.Filled.Mood, "Happy", Color(0xFF4CAF50), 5),
    CALM(Icons.Filled.Cloud, "Calm", Color(0xFF2196F3), 4),
    NEUTRAL(Icons.Filled.Face, "Neutral", Color(0xFF9E9E9E), 3),
    ANXIOUS(Icons.Filled.Warning, "Anxious", Color(0xFFFFC107), 2),
    SAD(Icons.Filled.SentimentDissatisfied, "Sad", Color(0xFF2196F3), 1)
}

@Composable
fun MoodTrackerScreen(
    navController: NavController,
    viewModel: MentalHealthViewModel
) {
    val moodEntries by viewModel.moodEntries.collectAsState()
    val quote by viewModel.quote.collectAsState()
    val advice by viewModel.advice.collectAsState()
    val activity by viewModel.activity.collectAsState()
    var selectedMood by remember { mutableStateOf<Mood?>(null) }
    var note by remember { mutableStateOf("") }
    var showNoteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchRandomQuote()
        viewModel.fetchAdvice()
        viewModel.fetchActivity()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "How are you feeling?",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Mood.values().forEach { mood ->
                            MoodButton(
                                mood = mood,
                                isSelected = selectedMood == mood,
                                onClick = {
                                    selectedMood = mood
                                    showNoteDialog = true
                                }
                            )
                        }
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Daily Inspiration",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    quote?.let { quoteResponse ->
                        Text(
                            text = "\"${quoteResponse.content}\"",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "- ${quoteResponse.author}",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Today's Advice",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    advice?.let { adviceResponse ->
                        Text(
                            text = adviceResponse.slip.advice,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Suggested Activity",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    activity?.let { activityResponse ->
                        Text(
                            text = activityResponse.activity,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Type: ${activityResponse.type}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Participants: ${activityResponse.participants}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        item {
            Text(
                text = "Mood History",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        items(moodEntries) { entry ->
            MoodEntryCard(entry)
        }
    }

    if (showNoteDialog) {
        AlertDialog(
            onDismissRequest = { showNoteDialog = false },
            title = { Text("Add a note") },
            text = {
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("How are you feeling?") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedMood?.let { mood ->
                            viewModel.addMoodEntry(mood.value, note)
                        }
                        note = ""
                        showNoteDialog = false
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNoteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun MoodButton(mood: Mood, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            onClick = onClick,
            shape = CircleShape,
            modifier = Modifier.size(64.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = if (isSelected) mood.color else Color.Transparent,
                contentColor = if (isSelected) Color.White else mood.color
            ),
            border = BorderStroke(
                width = 2.dp,
                color = mood.color
            )
        ) {
            Icon(
                imageVector = mood.icon,
                contentDescription = mood.label,
                modifier = Modifier.size(32.dp)
            )
        }
        
        Text(
            text = mood.label,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun MoodEntryCard(entry: MoodEntry) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
    val formattedDate = dateFormat.format(entry.timestamp)
    val mood = Mood.values().find { it.value == entry.mood } ?: Mood.NEUTRAL
    
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = mood.icon,
                    contentDescription = mood.label,
                    tint = mood.color,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = mood.label,
                    style = MaterialTheme.typography.titleMedium,
                    color = mood.color
                )
            }
            if (entry.note.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = entry.note,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (entry.activities.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Activities: ${entry.activities.joinToString(", ")}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = formattedDate,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
