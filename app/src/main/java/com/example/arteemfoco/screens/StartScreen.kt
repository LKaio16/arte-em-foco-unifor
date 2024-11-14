package com.example.arteemfoco.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore

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
            onClick = { navController.navigate("quizEnterCodeScreen") }) {
            androidx.compose.material3.Text("Jogar")
        }

        Spacer(Modifier.height(10.dp))

        Button(
            modifier = Modifier
                .width(250.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colorPrimary),
            onClick = { navController.navigate("obraScreen") }) {
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
            modifier = Modifier.width(250.dp).clickable { navController.navigate("loginScreen") },
            textAlign = TextAlign.End
        )

        // TESTE FIREBASE
       /* Button(
            modifier = Modifier
                .width(250.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colorPrimary),
            onClick = {
                val db = FirebaseFirestore.getInstance()
                val user = hashMapOf(
                    "first" to "Mario",
                    "last" to "Silva",
                    "born" to 1990
                )

                db.collection("users").add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d("Firestore", "DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w("Firestore", "Error adding document", e)
                    }
            }
        ) {
            androidx.compose.material3.Text("Adicionar ao Firestore")
        }



        Button(
            modifier = Modifier
                .width(250.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colorPrimary),
            onClick = {
                val db = FirebaseFirestore.getInstance()
                db.collection("users")
                    .whereEqualTo("born", 1990)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            db.collection("users").document(document.id).delete()
                                .addOnSuccessListener {
                                    Log.d("Firestore", "Document with ID: ${document.id} successfully deleted.")
                                }
                                .addOnFailureListener { e ->
                                    Log.w("Firestore", "Error deleting document", e)
                                }
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.w("Firestore", "Error finding documents", e)
                    }
            }
        ) {
            androidx.compose.material3.Text("Deletar Nascidos em 1990")
        }*/



    }
}

@Composable
@Preview
fun StartScreenPreview() {
    val navController = rememberNavController()
    StartScreen(navController)
}

