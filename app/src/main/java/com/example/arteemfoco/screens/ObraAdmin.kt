package com.example.arteemfoco.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.arteemfoco.screens.obras.ObraCard

@Composable
    fun ObraAdminScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Fundo branco para toda a tela
    ) {
        Text(
            text = "Obra",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp) // Espaçamento do topo
        )


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 0.dp) // Adiciona espaço suficiente para o título
        ) {
            Column(
                modifier = Modifier.clickable { },
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                ObraCard("Obra 1", "Vorem ipsum dolor sit amet, consectetur adipiscing elit.")
                Spacer(Modifier.height(10.dp))
                ObraCard("Obra 2", "Vorem ipsum dolor sit amet, consectetur adipiscing elit.")
                Spacer(Modifier.height(10.dp))
                ObraCard("Obra 3", "Vorem ipsum dolor sit amet, consectetur adipiscing elit.")
                Spacer(Modifier.height(10.dp))
                ObraCard("Obra 4", "Vorem ipsum dolor sit amet, consectetur adipiscing elit.")
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
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.White
            )
        }

    }
}

@Composable
@Preview
fun ObraAdminScreenPreview() {
    ObraAdminScreen()
}