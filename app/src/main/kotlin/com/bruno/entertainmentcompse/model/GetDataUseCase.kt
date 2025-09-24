package com.bruno.entertainmentcompse.model

import com.bruno.entertainmentcompse.data.GameRepository
import com.bruno.entertainmentcompse.ui.Resource
import javax.inject.Inject

class GetDataUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(amount: String, categoryId: Int): Resource<List<TriviaQuestion>> {
        return gameRepository.getData(amount = amount, categoryId = categoryId)
    }
}