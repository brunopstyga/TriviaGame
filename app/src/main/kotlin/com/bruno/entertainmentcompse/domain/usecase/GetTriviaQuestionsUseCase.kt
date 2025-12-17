package com.bruno.entertainmentcompse.domain.usecase

import com.bruno.entertainmentcompse.data.remote.TriviaQuestion
import com.bruno.entertainmentcompse.data.repository.GameRepositoryImpl
import com.bruno.entertainmentcompse.ui.Resource
import javax.inject.Inject

class GetTriviaQuestionsUseCase @Inject constructor(
    private val gameRepositoryImpl: GameRepositoryImpl
) {
    suspend operator fun invoke(
        categoryId: String,
        amount: Int,
        type: String,
        difficulty: String
    ): Resource<List<TriviaQuestion>> {
        return gameRepositoryImpl.getTriviaQuestions(
            categoryId = categoryId,
            amount = amount,
            type = type,
            difficulty = difficulty
        )
    }
}