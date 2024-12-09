package com.example.arteemfoco.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun RegisterScreen(navController: NavController) {
    // Recupera as cores do tema
    val colorPrimary = androidx.compose.material3.MaterialTheme.colorScheme.primary
    val backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.background

    // Estados para os campos de texto
    val (username, setUsername) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val (repeatPassword, setRepeatPassword) = remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
    ) {
        Text(
            text = "Registrar-se",
            fontSize = 24.sp,
            fontWeight = FontWeight.W900,
            color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(20.dp))

        TextField(
            value = username,
            onValueChange = setUsername,
            label = { Text("Usuário") },
            modifier = Modifier.width(250.dp)
        )
        Spacer(Modifier.height(10.dp))

        TextField(
            value = password,
            onValueChange = setPassword,
            label = { Text("Senha") },
            modifier = Modifier.width(250.dp)
        )
        Spacer(Modifier.height(10.dp))

        TextField(
            value = repeatPassword,
            onValueChange = setRepeatPassword,
            label = { Text("Repetir Senha") },
            modifier = Modifier.width(250.dp)
        )
        Spacer(Modifier.height(20.dp))

        // Botões
        Row(
            modifier = Modifier.width(250.dp)
        ) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF8B0000)
                ),
                onClick = { navController.popBackStack() }
            ) {
                Text("Voltar")
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorPrimary
                ),
                onClick = {
                    // Lógica para registrar o usuário
                }
            ) {
                Text("Cadastrar")
            }
        }
    }
}

@Composable
@Preview
fun RegisterScreenPreview() {
    val navController = rememberNavController()
    RegisterScreen(navController)
}
