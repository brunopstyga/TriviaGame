package com.bruno.entertainmentcompse.domain.usecase

import com.bruno.entertainmentcompse.data.remote.TriviaQuestion
import com.bruno.entertainmentcompse.data.repository.GameRepositoryImpl
import com.bruno.entertainmentcompse.ui.Resource
import javax.inject.Inject

class GetDataUseCase @Inject constructor(
    private val gameRepositoryImpl: GameRepositoryImpl
) {
    suspend operator fun invoke(amount: String, categoryId: Int): Resource<List<TriviaQuestion>> {
        return gameRepositoryImpl.getData(amount = amount, categoryId = categoryId)
    }
}