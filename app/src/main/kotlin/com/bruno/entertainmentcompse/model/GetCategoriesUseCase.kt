package com.bruno.entertainmentcompse.model

import com.bruno.entertainmentcompse.data.GameRepository
import com.bruno.entertainmentcompse.ui.Resource
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(): Resource<CategoryTrivia> {
        return gameRepository.getCategories()
    }
}