package com.bruno.entertainmentcompse.model

import com.bruno.entertainmentcompse.data.GameRepository
import com.bruno.entertainmentcompse.ui.Resource
import javax.inject.Inject

class GetTriviaQuestionsUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(
        categoryId: String,
        amount: Int,
        type: String,
        difficulty: String
    ): Resource<List<TriviaQuestion>> {
        return gameRepository.getTriviaQuestions(
            categoryId = categoryId,
            amount = amount,
            type = type,
            difficulty = difficulty
        )
    }
}