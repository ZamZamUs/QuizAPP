package com.example.pawal.data.repository

import com.example.pawal.data.local.dao.QuizDao
import com.example.pawal.data.local.entity.Category
import com.example.pawal.data.local.entity.CategoryWithCount
import com.example.pawal.data.local.entity.Question
import com.example.pawal.data.local.entity.QuizResult
import com.example.pawal.data.local.entity.ResultWithCategory
import kotlinx.coroutines.flow.Flow

class QuizRepository(private val quizDao: QuizDao) {
    val allCategoriesWithCount: Flow<List<CategoryWithCount>> = quizDao.getCategoriesWithCount()
    val allResults: Flow<List<ResultWithCategory>> = quizDao.getAllResultsWithCategory()

    suspend fun insertCategory(category: Category) = quizDao.insertCategory(category)
    suspend fun updateCategory(category: Category) = quizDao.updateCategory(category)
    suspend fun deleteCategory(category: Category) = quizDao.deleteCategory(category)
    suspend fun getCategoryById(id: Int) = quizDao.getCategoryById(id)

    fun getQuestionsByCategory(categoryId: Int): Flow<List<Question>> = quizDao.getQuestionsByCategory(categoryId)
    suspend fun getQuestionsByCategoryList(categoryId: Int): List<Question> = quizDao.getQuestionsByCategoryList(categoryId)
    fun getQuestionCountByCategory(categoryId: Int): Flow<Int> = quizDao.getQuestionCountByCategory(categoryId)
    suspend fun insertQuestion(question: Question) = quizDao.insertQuestion(question)
    suspend fun updateQuestion(question: Question) = quizDao.updateQuestion(question)
    suspend fun deleteQuestion(question: Question) = quizDao.deleteQuestion(question)

    suspend fun insertResult(result: QuizResult) = quizDao.insertResult(result)
}
