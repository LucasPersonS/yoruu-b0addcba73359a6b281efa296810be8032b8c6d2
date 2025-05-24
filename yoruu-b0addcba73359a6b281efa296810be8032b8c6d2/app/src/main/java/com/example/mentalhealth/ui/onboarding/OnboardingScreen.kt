package com.example.mentalhealth.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mentalhealth.R

@Composable
fun OnboardingScreen(step: Int, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        // Content based on step
        when (step) {
            1 -> OnboardingStep1()
            2 -> OnboardingStep2()
            3 -> OnboardingStep3()
        }
        
        // Navigation buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (step > 1) {
                Button(
                    onClick = { 
                        navController.navigate("onboarding/${step - 1}")
                    },
                    modifier = Modifier.width(120.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Anterior")
                }
            } else {
                Spacer(modifier = Modifier.width(120.dp))
            }
            
            if (step < 3) {
                Button(
                    onClick = { 
                        navController.navigate("onboarding/${step + 1}")
                    },
                    modifier = Modifier.width(120.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Próximo")
                }
            } else {
                Button(
                    onClick = { 
                        navController.navigate("main") {
                            popUpTo("onboarding/1") { inclusive = true }
                        }
                    },
                    modifier = Modifier.width(120.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Começar")
                }
            }
        }
    }
}

@Composable
fun OnboardingStep1() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Placeholder for image
        Box(
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            // Replace with actual image resource
            Image(
                painter = painterResource(id = android.R.drawable.ic_dialog_info),
                contentDescription = "Welcome",
                modifier = Modifier.size(120.dp)
            )
        }
        
        Text(
            text = "Bem-vindo ao suporte de saúde mental",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Seu companheiro pessoal para bem-estar mental e suporte",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun OnboardingStep2() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Placeholder for image
        Box(
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            // Replace with actual image resource
            Image(
                painter = painterResource(id = android.R.drawable.ic_dialog_email),
                contentDescription = "Chat with Yoru",
                modifier = Modifier.size(120.dp)
            )
        }
        
        Text(
            text = "Conheça Yoru, seu terapeuta virtual",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Fale com Yoru quando precisar de alguém para conversar ou buscar conselhos",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun OnboardingStep3() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Placeholder for image
        Box(
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            // Replace with actual image resource
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_myplaces),
                contentDescription = "Track Mood",
                modifier = Modifier.size(120.dp)
            )
        }
        
        Text(
            text = "Acompanhe seu humor",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Acompanhe seu bem-estar emocional e veja padrões ao longo do tempo",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}
