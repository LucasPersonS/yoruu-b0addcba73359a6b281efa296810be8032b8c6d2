@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodTrackerScreen(
    viewModel: MentalHealthViewModel
) {
    val moodEntries by viewModel.moodEntries.collectAsState()
    var selectedMood by remember { mutableStateOf<Mood?>(null) }
    var noteText by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Text(
            text = "Como você está se sentindo hoje?",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        // Mood selection
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(Mood.values()) { mood ->
                MoodButton(
                    mood = mood,
                    isSelected = selectedMood == mood,
                    onClick = { selectedMood = mood }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Note input
        OutlinedTextField(
            value = noteText,
            onValueChange = { noteText = it },
            label = { Text("Adicione uma nota (opcional)") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            minLines = 3
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Save button
        Button(
            onClick = {
                selectedMood?.let { mood ->
                    // Convert Mood enum to Int (1-5 scale)
                    val moodValue = when (mood) {
                        Mood.HAPPY -> 5
                        Mood.CALM -> 4
                        Mood.NEUTRAL -> 3
                        Mood.ANXIOUS -> 2
                        Mood.SAD -> 2
                        Mood.ANGRY -> 1
                    }
                    viewModel.addMoodEntry(moodValue, noteText)
                    selectedMood = null
                    noteText = ""
                }
            },
            enabled = selectedMood != null,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Salvar humor")
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Recent entries
        if (moodEntries.isNotEmpty()) {
            Text(
                text = "Entradas recentes",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            moodEntries.sortedByDescending { it.timestamp }.take(5).forEach { entry ->
                MoodEntryCard(entry = entry)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun MoodEntryCard(entry: MoodEntry) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy - HH:mm", Locale.getDefault())
    val formattedDate = dateFormat.format(entry.timestamp)
    
    // Convert mood value to Mood enum for display
    val mood = when (entry.mood) {
        5 -> Mood.HAPPY
        4 -> Mood.CALM
        3 -> Mood.NEUTRAL
        2 -> if (entry.note.contains("anxious", ignoreCase = true)) Mood.ANXIOUS else Mood.SAD
        1 -> Mood.ANGRY
        else -> Mood.NEUTRAL
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
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
                
                Spacer(modifier = Modifier.weight(1f))
                
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            
            if (entry.note.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = entry.note,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
} 