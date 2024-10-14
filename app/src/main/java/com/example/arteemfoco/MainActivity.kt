package com.example.arteemfoco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.arteemfoco.ui.theme.ArteEmFocoTheme
import com.example.arteemfoco.ui.theme.Purple80
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            ArteEmFocoTheme {
//                teste cores nav
                val colorPrimary = MaterialTheme.colorScheme.primary
                window.navigationBarColor = Purple80.toArgb()

                NavHost(navController = navController, startDestination = "tela1") {
                    // Navegação para as telas sem BottomBar
                    composable("tela1") { Tela1(navController) }
                    composable("tela2") { Tela2(navController) }

                    // Navegação para a MainScreen (com BottomBar)
                    composable("mainScreen") { MainScreen() }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ArteEmFocoTheme {
        Greeting("Android")
    }
}


@Composable
fun Tela1(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Tela 1")
        CaixaTexto()
        val colorPrimary = MaterialTheme.colorScheme.primary
//      teste cor
        Button(modifier = Modifier.background(colorPrimary),onClick = { navController.navigate("tela2") }) {
            Text("Ir para Tela 2")
        }
    }
}

@Composable
fun Tela2(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Tela 2")
        Button(onClick = { navController.navigate("tela1") }) {
            Text("Voltar para Tela 1")
        }
        // Botão para navegar para a MainScreen (com BottomBar)
        Button(onClick = { navController.navigate("mainScreen") }) {
            Text("Ir para MainScreen")
        }
    }
}


@Composable
fun CaixaTexto() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
    ) {

        var textState by remember { mutableStateOf("") }


        TextField(value = textState, onValueChange = {
            textState = it
        }, modifier = Modifier.fillMaxWidth())

    }
}

@Composable
fun LabelInput(text: String) {
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = text)
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    MainScreen()
}