package com.example.mentalhealth.ui.main.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mentalhealth.R
import com.example.mentalhealth.ui.viewmodel.MentalHealthViewModel
import com.example.mentalhealth.data.model.User
import com.example.mentalhealth.data.model.MoodEntry
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: MentalHealthViewModel
) {
    val currentUser by viewModel.currentUser.collectAsState()
    val moodEntries by viewModel.moodEntries.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile header
        ProfileHeader(user = currentUser)
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Mood history
        if (moodEntries.isNotEmpty()) {
            Text(
                text = "Mood History",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            
            moodEntries.sortedByDescending { it.timestamp }.take(5).forEach { entry ->
                MoodEntryCard(entry = entry)
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
        
        // Profile options
        ProfileOptions(navController)
    }
}

@Composable
fun ProfileHeader(user: User?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile image
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            // Replace with actual profile image
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                contentDescription = "Profile Picture",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // User name
        Text(
            text = user?.name ?: "Nome do usuário",
            style = MaterialTheme.typography.headlineSmall
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        // User email
        Text(
            text = user?.email ?: "email@exemplo.com",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Edit profile button
        OutlinedButton(
            onClick = { /* Edit profile action */ },
            shape = RoundedCornerShape(20.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Edit Profile")
        }
    }
}

@Composable
fun MoodEntryCard(entry: MoodEntry) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy - HH:mm", Locale.getDefault())
    val formattedDate = dateFormat.format(entry.timestamp)
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Mood: ${entry.mood}/5",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
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
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ProfileOptions(navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ProfileOptionItem(
                icon = Icons.Filled.Notifications,
                title = "Notifications",
                onClick = { /* Notifications action */ }
            )
            
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            
            ProfileOptionItem(
                icon = Icons.Filled.Lock,
                title = "Privacy & Security",
                onClick = { /* Privacy action */ }
            )
            
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            
            ProfileOptionItem(
                icon = Icons.Filled.Help,
                title = "Help & Support",
                onClick = { /* Help action */ }
            )
            
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            
            ProfileOptionItem(
                icon = Icons.Filled.Info,
                title = "About",
                onClick = { /* About action */ }
            )
            
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            
            ProfileOptionItem(
                icon = Icons.Filled.ExitToApp,
                title = "Logout",
                onClick = { 
                    navController.navigate("login") {
                        popUpTo("main") { inclusive = true }
                    }
                }
            )
        }
    }
}

@Composable
fun ProfileOptionItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "Navigate",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
