package com.bruno.entertainmentcompse.domain.repository

import com.bruno.entertainmentcompse.data.remote.CategoryTrivia
import com.bruno.entertainmentcompse.data.remote.TriviaQuestion
import com.bruno.entertainmentcompse.ui.Resource

interface GameRepository {

    suspend fun getCategories(): Resource<CategoryTrivia>

    suspend fun getTriviaQuestions(
        categoryId: String,
        amount: Int,
        type: String,
        difficulty: String
    ): Resource<List<TriviaQuestion>>

    suspend fun getData(
        amount: String,
        categoryId: Int
    ): Resource<List<TriviaQuestion>>

}