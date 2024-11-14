package com.example.arteemfoco.screens.obras

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun ObraViewScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Fundo branco para toda a tela
    ) {
        Box(
            modifier = Modifier.padding(start = 17.dp, top = 37.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Voltar",
                tint = Color.Black,
                modifier = Modifier
                    .clickable { navController.popBackStack() }
            )
        }



        Text(
            text = "Obras",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp),
            textAlign = TextAlign.Center
        )


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            // Imagem
            Box(
                modifier = Modifier
                    .background(Color.Gray)
                    .size(300.dp, 150.dp)
            )

            // Pergunta
            Column(
                modifier = Modifier
                    .width(300.dp)
                    .padding(16.dp), // Espaçamento interno
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp) // Espaçamento entre os textos
            ) {

                Text(
                    text = "Pergunta 1",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )


                Text(
                    text = "Autor Genérico",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )

                Spacer(Modifier.height(10.dp))

                Text(
                    text = "Korem ipsum dolor sit amet, consectetur adipiscing elit. " +
                            "Nunc vulputate libero et velit interdum, ac aliquet odio mattis. Class aptent taciti sociosqu ad litora torquent per " +
                            "conubia nostra, per inceptos himenaeos.Korem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vulputate libero et velit interdum, ac aliquet odio mattis. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos.Korem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vulputate libero et velit interdum, ac aliquet odio mattis. Class aptent taciti sociosqu ad litora torquent per c",
                    fontSize = 12.sp, textAlign = TextAlign.Center
                )
            }


        }

        FloatingActionButton(
            onClick = { /* Ação ao clicar no botão */ },
            backgroundColor = Color.Gray,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 50.dp, end = 20.dp) // Padding para afastar do canto
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Chat,
                contentDescription = "Chat",
                tint = Color.White
            )
        }

    }
}

@Composable
@Preview
fun ObraViewScreenPreview() {
    val navController = rememberNavController()
    ObraViewScreen(navController)
}
