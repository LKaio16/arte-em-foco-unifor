package com.example.arteemfoco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.arteemfoco.screens.LoginScreen
import com.example.arteemfoco.screens.RegisterScreen
import com.example.arteemfoco.screens.StartScreen
import com.example.arteemfoco.screens.quiz.QuizEndScreen
import com.example.arteemfoco.screens.quiz.QuizEnterCodeScreen
import com.example.arteemfoco.screens.quiz.QuizEnterNameScreen
import com.example.arteemfoco.screens.quiz.QuizEnterNameScreenPreview
import com.example.arteemfoco.screens.quiz.QuizScreen
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

                val colorPrimary = MaterialTheme.colorScheme.primary
                window.navigationBarColor = Purple80.toArgb()

                NavHost(navController = navController, startDestination = "startScreen") {
                    // Navegação para as telas sem BottomBar
                    composable("startScreen") { StartScreen(navController) }
                    composable("tela2") { tela2(navController) }
                    composable("loginScreen") { LoginScreen(navController) }
                    composable("registerScreen") { RegisterScreen(navController) }
                    composable("quizEnterCodeScreen") { QuizEnterCodeScreen(navController) }
                    composable("quizEnterNameScreen") { QuizEnterNameScreen(navController) }
                    composable("quizScreen") { QuizScreen(navController) }
                    composable("quizEndScreen") { QuizEndScreen(navController) }


                    // Navegação para a MainScreen (com BottomBar)
                    composable("mainScreen") { MainScreen() }

                }
            }
        }
    }
}


@Composable
fun tela2(navController: NavController) {
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
fun CaixaTexto(placeholder: String) {
    Box(
        modifier = Modifier
            .width(250.dp)
            .clip(RoundedCornerShape(10.dp))
            .height(45.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
    ) {
        var textState by remember { mutableStateOf("") }

        BasicTextField(
            value = textState,
            onValueChange = { textState = it },
            modifier = Modifier
                .width(250.dp)
                .background(Color.Transparent),
            textStyle = LocalTextStyle.current.copy(
                color = Color.Black,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            ),
            decorationBox = { innerTextField ->
                if (textState.isEmpty()) {
                    Text(
                        text = placeholder,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.LightGray,
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp
                    )
                }
                innerTextField()
            },
            singleLine = true
        )
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
//
//@Preview
//@Composable
//private fun MainScreenPreview() {
//    MainScreen()
//}

@Preview
@Composable
private fun CuPreview() {
    CaixaTexto("Aaaa")
}
