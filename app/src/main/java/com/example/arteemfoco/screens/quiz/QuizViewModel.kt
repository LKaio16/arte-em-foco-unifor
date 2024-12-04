import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arteemfoco.screens.admin.Question
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class Quiz(
    val id: String = "",
    val title: String = "",
    val perguntas: List<Question> = emptyList()
)

class QuizViewModel : ViewModel() {
    private val _quiz = MutableStateFlow<Quiz?>(null)
    val quiz: StateFlow<Quiz?> = _quiz

    private val firestore = FirebaseFirestore.getInstance()

    private var loadedQuizId: String? = null // Armazena o ID correto do quiz carregado

    fun saveResults(userName: String, correctAnswers: Int) {
        val quizId = loadedQuizId
        if (quizId == null) {
            println("Erro: quizId não carregado corretamente!")
            return
        }

        val response = mapOf(
            "name" to userName,
            "correctAnswers" to correctAnswers
        )
        firestore.collection("quizzes")
            .document(quizId)
            .collection("respostas")
            .add(response)
            .addOnSuccessListener {
                println("Resultados salvos com sucesso!")
            }
            .addOnFailureListener { e ->
                println("Erro ao salvar resultados: ${e.message}")
            }
    }

    fun loadQuiz(code: String) {
        viewModelScope.launch {
            firestore.collection("quizzes")
                .whereEqualTo("code", code) // Procurando pelo campo 'code' no Firestore
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val document = querySnapshot.documents.firstOrNull()
                    if (document != null) {
                        val quizId = document.id // O ID correto do quiz
                        loadedQuizId = quizId // Armazena o ID carregado

                        val title = document.getString("title") ?: "Sem título"
                        val perguntasCollection = firestore.collection("quizzes")
                            .document(quizId)
                            .collection("perguntas")

                        perguntasCollection.get().addOnSuccessListener { perguntasSnapshot ->
                            val perguntas = perguntasSnapshot.map { perguntaDoc ->
                                Question(
                                    title = perguntaDoc.getString("title") ?: "Pergunta",
                                    description = perguntaDoc.getString("description") ?: "Descrição",
                                    alternatives = perguntaDoc.get("alternatives") as List<String>,
                                    correctAnswerIndex = perguntaDoc.getLong("correctAnswerIndex")?.toInt() ?: -1
                                )
                            }

                            _quiz.value = Quiz(id = quizId, title = title, perguntas = perguntas)
                        }
                    } else {
                        println("Erro: Nenhum quiz encontrado com o código fornecido!")
                    }
                }
                .addOnFailureListener { exception ->
                    println("Erro ao buscar quiz: ${exception.message}")
                }
        }
    }
}





