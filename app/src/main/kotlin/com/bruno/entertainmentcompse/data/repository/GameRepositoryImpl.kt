package com.bruno.entertainmentcompse.data.repository

import com.bruno.entertainmentcompse.data.remote.CategoryTrivia
import com.bruno.entertainmentcompse.data.remote.TriviaQuestion
import com.bruno.entertainmentcompse.data.remote.ApiGame
import com.bruno.entertainmentcompse.domain.repository.GameRepository
import com.bruno.entertainmentcompse.ui.Resource
import timber.log.Timber
import javax.inject.Inject


class GameRepositoryImpl @Inject constructor(
    private val apiGame: ApiGame
): GameRepository {

   override suspend fun getCategories(): Resource<CategoryTrivia> {
        val response = try {
            apiGame.getCategories()
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured: ${e.localizedMessage}")
        }

        return Resource.Success(response)
    }

    override suspend fun getTriviaQuestions( categoryId: String,
                                       amount: Int,
                                       type: String,
                                    difficulty: String):
            Resource<List<TriviaQuestion>>  {

        val response = try {
            Timber.tag("GameRepository").d("Le estamos enviando: category=$categoryId, type=$type, difficulty=$difficulty")
            apiGame.getQuestions(category= categoryId,amount = amount, type = type, difficulty = difficulty)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured: ${e.localizedMessage}")
        }

        return Resource.Success(response.questions)
    }

    override suspend fun getData(amount: String, categoryId: Int): Resource<List<TriviaQuestion>> {
        val response = try {
            apiGame.getData(amount = amount, category = categoryId)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred: ${e.localizedMessage}")
        }

        return if (response.isSuccessful) {
            response.body()?.let { body ->
                Resource.Success(body.questions)
            } ?: Resource.Error("Empty response body")
        } else {
            Resource.Error("Error fetching data: ${response.message()}")
        }
    }
}