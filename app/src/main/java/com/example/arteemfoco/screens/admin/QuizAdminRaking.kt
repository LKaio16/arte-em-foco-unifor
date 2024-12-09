package com.example.arteemfoco.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

data class Ranking(
    val name: String,
    val correctAnswers: Int,
)

@Composable
fun RankingScreen(navController: NavController, quizId: String) {
    var rankings by remember { mutableStateOf(listOf<Ranking>()) }

    // Buscar rankings do Firestore
    LaunchedEffect(quizId) {
        val db = FirebaseFirestore.getInstance()
        db.collection("quizzes")
            .document(quizId)
            .collection("respostas")
            .orderBy("correctAnswers", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                val rankingList = result.map { doc ->
                    Ranking(
                        name = doc.getString("name") ?: "Sem Nome",
                        correctAnswers = doc.getLong("correctAnswers")?.toInt() ?: 0
                    )
                }
                rankings = rankingList
            }
            .addOnFailureListener { exception ->
                println("Erro ao carregar rankings: ${exception.message}")
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Topo com título "Ranking"
        Box {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Voltar",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(start = 17.dp, top = 40.dp)
                    .clickable { navController.popBackStack() }
                    .size(30.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
            ) {

                Text(
                    text = "Ranking",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de rankings
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(rankings) { ranking ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = ranking.name,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "${ranking.correctAnswers} acertos",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold
                    )
                }
                Divider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                    thickness = 1.dp
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RankingScreenPreview() {
    val navController = rememberNavController()
    RankingScreen(navController, "quiz123")
}
