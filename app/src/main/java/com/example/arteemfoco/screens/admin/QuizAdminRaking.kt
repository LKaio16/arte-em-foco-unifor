package com.example.arteemfoco.screens.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


data class Ranking(
    val name: String,
    val correctAnswers: Int
)


@Composable
fun RankingScreen(navController: NavController, quizId: String) {
    var rankings by remember { mutableStateOf(listOf<Ranking>()) }

    // Buscar rankings do Firestore
    LaunchedEffect(quizId) {
        val db = FirebaseFirestore.getInstance()
        db.collection("quizzes")
            .document(quizId) // Acessa o documento do quiz específico
            .collection("respostas") // Acessa a subcoleção "respostas"
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
                println("Rankings carregados: $rankingList")
            }
            .addOnFailureListener { exception ->
                println("Erro ao carregar rankings: ${exception.message}")
            }
    }



    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Ranking - Quiz $quizId",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(rankings) { ranking ->
                Text(
                    text = "${ranking.name} - ${ranking.correctAnswers} acertos",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

