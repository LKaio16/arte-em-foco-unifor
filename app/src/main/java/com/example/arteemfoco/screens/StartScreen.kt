package com.example.arteemfoco.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.arteemfoco.CaixaTexto

@Composable
fun StartScreen(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {

        Spacer(Modifier.height(10.dp))

        Spacer(Modifier.height(30.dp))
        val colorPrimary = androidx.compose.material3.MaterialTheme.colorScheme.primary
//      teste cor

        Box(
            modifier = Modifier
                .background(Color.Gray)
                .size(250.dp, 150.dp)
        ) {
        }

        Spacer(Modifier.height(20.dp))

        Button(
            modifier = Modifier
                .width(250.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colorPrimary),
            onClick = { navController.navigate("tela2") }) {
            androidx.compose.material3.Text("Jogar")
        }

        Spacer(Modifier.height(10.dp))

        Button(
            modifier = Modifier
                .width(250.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colorPrimary),
            onClick = { navController.navigate("tela2") }) {
            androidx.compose.material3.Text("Ver Obras")
        }


        Spacer(Modifier.height(10.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) { // Sublinhado em todo o texto
                    append("Vorem ipsum dolor ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("sitamet.")
                    }
                }
            },
            color = Color.Black.copy(alpha = 0.5f), // Reduzir opacidade
            modifier = Modifier.width(250.dp),
            textAlign = TextAlign.End
        )

    }
}

@Composable
@Preview
fun StartScreenPreview() {
    val navController = rememberNavController()
    StartScreen(navController)
}

