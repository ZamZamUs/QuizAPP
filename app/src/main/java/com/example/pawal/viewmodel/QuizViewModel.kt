package com.example.pawal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawal.data.local.entity.Category
import com.example.pawal.data.local.entity.Question
import com.example.pawal.data.local.entity.QuizResult
import com.example.pawal.data.repository.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizViewModel(private val repository: QuizRepository) : ViewModel() {

    val categoriesWithCount = repository.allCategoriesWithCount
    val quizHistory = repository.allResults

    // Category Management
    fun addCategory(name: String) {
        viewModelScope.launch {
            repository.insertCategory(Category(namaTopik = name))
        }
    }

    fun updateCategory(category: Category) {
        viewModelScope.launch {
            repository.updateCategory(category)
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            repository.deleteCategory(category)
        }
    }

    // Question Management
    fun getQuestionsByCategory(categoryId: Int) = repository.getQuestionsByCategory(categoryId)

    fun addQuestion(question: Question) {
        viewModelScope.launch {
            repository.insertQuestion(question)
        }
    }

    fun updateQuestion(question: Question) {
        viewModelScope.launch {
            repository.updateQuestion(question)
        }
    }

    fun deleteQuestion(question: Question) {
        viewModelScope.launch {
            repository.deleteQuestion(question)
        }
    }

    // Quiz Logic
    private val _currentQuizQuestions = MutableStateFlow<List<Question>>(emptyList())
    val currentQuizQuestions = _currentQuizQuestions.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex = _currentQuestionIndex.asStateFlow()

    private val _userAnswers = MutableStateFlow<Map<Int, String>>(emptyMap())
    val userAnswers = _userAnswers.asStateFlow()

    private val _quizResult = MutableStateFlow<QuizResult?>(null)
    val quizResult = _quizResult.asStateFlow()

    fun startQuiz(categoryId: Int) {
        viewModelScope.launch {
            val questions = repository.getQuestionsByCategoryList(categoryId)
            _currentQuizQuestions.value = questions.shuffled()
            _currentQuestionIndex.value = 0
            _userAnswers.value = emptyMap()
            _quizResult.value = null
        }
    }

    fun submitAnswer(answer: String) {
        val currentIndex = _currentQuestionIndex.value
        val updatedAnswers = _userAnswers.value.toMutableMap()
        updatedAnswers[currentIndex] = answer
        _userAnswers.value = updatedAnswers
    }

    fun nextQuestion() {
        if (_currentQuestionIndex.value < (_currentQuizQuestions.value.size - 1)) {
            _currentQuestionIndex.value += 1
        }
    }

    fun finishQuiz(categoryId: Int) {
        val questions = _currentQuizQuestions.value
        val answers = _userAnswers.value
        var correctCount = 0

        questions.forEachIndexed { index, question ->
            if (answers[index] == question.kunciJawaban) {
                correctCount++
            }
        }

        val score = if (questions.isNotEmpty()) (correctCount.toDouble() / questions.size * 100).toInt() else 0
        val result = QuizResult(
            categoryId = categoryId,
            skor = score,
            tanggalMain = System.currentTimeMillis(),
        )

        viewModelScope.launch {
            repository.insertResult(result)
            _quizResult.value = result
        }
    }
}
