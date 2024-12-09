package com.example.arteemfoco.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.arteemfoco.Screen

@Composable
fun LoginScreen(navController: NavController) {
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        androidx.compose.material3.Text(
            text = "Login",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(20.dp))

        TextField(
            value = user,
            onValueChange = { user = it },
            label = { androidx.compose.material3.Text("Usuário") },
            modifier = Modifier
                .width(300.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(10.dp),
            )

        Spacer(Modifier.height(10.dp))


        TextField(
            value = password,
            onValueChange = { password = it },
            label = { androidx.compose.material3.Text("Senha") },
            modifier = Modifier
                .width(300.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(10.dp),
        )


        Spacer(Modifier.height(20.dp))

        Button(
            modifier = Modifier
                .width(250.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.primary),
            onClick = {
                navController.navigate(Screen.Main.route)
            }
        ) {
            androidx.compose.material3.Text("Entrar")
        }

        Spacer(Modifier.height(10.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) { // Sublinhado em todo o texto
                    append("Não possui conta? ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("Cadastre-se.")
                    }
                }
            },
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f), // Reduzir opacidade
            modifier = Modifier
                .width(250.dp)
                .clickable { navController.navigate("registerScreen") },
            textAlign = TextAlign.End
        )

    }

}

@Composable
@Preview
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(navController)
}
