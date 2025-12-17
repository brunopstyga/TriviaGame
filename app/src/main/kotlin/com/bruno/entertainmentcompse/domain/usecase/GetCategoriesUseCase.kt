package com.bruno.entertainmentcompse.domain.usecase

import com.bruno.entertainmentcompse.data.remote.CategoryTrivia
import com.bruno.entertainmentcompse.data.repository.GameRepositoryImpl
import com.bruno.entertainmentcompse.ui.Resource
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val gameRepositoryImpl: GameRepositoryImpl
) {
    suspend operator fun invoke(): Resource<CategoryTrivia> {
        return gameRepositoryImpl.getCategories()
    }
}