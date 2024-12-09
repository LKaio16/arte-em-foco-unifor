package com.example.arteemfoco.screens

import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.squareup.picasso.Picasso

@Composable
fun StartScreen(navController: NavController) {
    // Recupera as cores do tema atual
    val colorPrimary = androidx.compose.material3.MaterialTheme.colorScheme.primary
    val backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.background
    val textColor = androidx.compose.material3.MaterialTheme.colorScheme.onBackground

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor) // Usa a cor de fundo do tema
    ) {

        Spacer(Modifier.height(10.dp))

        Spacer(Modifier.height(30.dp))

        AndroidView(
            modifier = Modifier
                .size(245.dp, 146.dp)
                .clip(RoundedCornerShape(16.dp)),
            factory = { context ->
                ImageView(context).apply {
                    Picasso.get()
                        .load("https://upload.wikimedia.org/wikipedia/commons/0/08/Sem_t%C3%ADtulo-removebg-preview.png")
                        .fit() // Ajusta a imagem para caber sem cortar
                        .centerInside() // Garante que a imagem fique dentro dos limites
                        .into(this)
                }
            }
        )

        Spacer(Modifier.height(20.dp))

        Button(
            modifier = Modifier
                .width(250.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(10.dp)),
            onClick = { navController.navigate("quizEnterCodeScreen") }
        ) {
            androidx.compose.material3.Text(
                text = "Jogar",
                fontWeight = FontWeight.Bold, // Define o texto como bold
                fontSize = 16.sp, // Define um tamanho de texto, caso queira ajustar
                color = MaterialTheme.colorScheme.onPrimary // Garante contraste
            )
        }


        Spacer(Modifier.height(15.dp))

        Button(
            modifier = Modifier
                .width(250.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(10.dp)),
            onClick = { navController.navigate("obraScreen") }
        ) {
            androidx.compose.material3.Text(
                text = "Ver Obras",
                fontWeight = FontWeight.Bold, // Define o texto como bold
                fontSize = 15.sp, // Define um tamanho de texto, caso queira ajustar
                color = MaterialTheme.colorScheme.onPrimary // Garante contraste
            )
        }

        Spacer(Modifier.height(10.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline,)) { // Sublinhado em todo o texto
                    append("Entrar como ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                        )
                    ) {
                        append("Administrador.")
                    }
                }
            },
            color = textColor.copy(alpha = 0.5f), // Usa a cor de texto do tema com opacidade reduzida
            fontSize = 15.sp,
            modifier = Modifier
                .width(250.dp)
                .clickable { navController.navigate("loginScreen") },
            textAlign = TextAlign.End,

        )
    }
}


@Composable
@Preview
fun StartScreenPreview() {
    val navController = rememberNavController()
    StartScreen(navController)
}

