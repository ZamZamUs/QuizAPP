package com.example.pawal.data.local.dao

import androidx.room.*
import com.example.pawal.data.local.entity.Category
import com.example.pawal.data.local.entity.Question
import com.example.pawal.data.local.entity.QuizResult
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {
    // Category
    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<Category>>

    @Query("""
        SELECT c.id, c.namaTopik, COUNT(q.id) as questionCount 
        FROM categories c 
        LEFT JOIN questions q ON c.id = q.categoryId 
        GROUP BY c.id
    """)
    fun getCategoriesWithCount(): Flow<List<com.example.pawal.data.local.entity.CategoryWithCount>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Update
    suspend fun updateCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategoryById(id: Int): Category?

    // Question
    @Query("SELECT * FROM questions WHERE categoryId = :categoryId")
    fun getQuestionsByCategory(categoryId: Int): Flow<List<Question>>
    
    @Query("SELECT * FROM questions WHERE categoryId = :categoryId")
    suspend fun getQuestionsByCategoryList(categoryId: Int): List<Question>

    @Query("SELECT COUNT(*) FROM questions WHERE categoryId = :categoryId")
    fun getQuestionCountByCategory(categoryId: Int): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: Question)

    @Update
    suspend fun updateQuestion(question: Question)

    @Delete
    suspend fun deleteQuestion(question: Question)

    // Quiz Result
    @Query("""
        SELECT r.id, r.categoryId, c.namaTopik as categoryName, r.skor, r.tanggalMain 
        FROM quiz_results r 
        INNER JOIN categories c ON r.categoryId = c.id 
        ORDER BY r.tanggalMain DESC
    """)
    fun getAllResultsWithCategory(): Flow<List<com.example.pawal.data.local.entity.ResultWithCategory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: QuizResult)
}
