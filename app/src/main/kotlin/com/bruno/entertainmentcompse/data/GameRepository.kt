package com.bruno.entertainmentcompse.data

import com.bruno.entertainmentcompse.model.CategoryTrivia
import com.bruno.entertainmentcompse.model.TriviaQuestion
import com.bruno.entertainmentcompse.model.TriviaResponse
import com.bruno.entertainmentcompse.service.ApiGame
import com.bruno.entertainmentcompse.ui.Resource
import timber.log.Timber
import javax.inject.Inject


class GameRepository @Inject constructor(
    private val gameRepository: ApiGame){

    suspend fun getCategories(): Resource<CategoryTrivia> {
        val response = try {
            gameRepository.getCategories()
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured: ${e.localizedMessage}")
        }

        return Resource.Success(response)
    }

    suspend fun getTriviaQuestions( categoryId: String,
                                       amount: Int,
                                       type: String,
                                    difficulty: String):
            Resource<List<TriviaQuestion>>  {

        val response = try {
            Timber.tag("GameRepository").d("Le estamos enviando: category=$categoryId, type=$type, difficulty=$difficulty")
            gameRepository.getQuestions(category= categoryId,amount = amount, type = type, difficulty = difficulty)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured: ${e.localizedMessage}")
        }

        return Resource.Success(response.questions)
    }

    suspend fun getData(amount: String, categoryId: Int): Resource<List<TriviaQuestion>> {
        val response = try {
            gameRepository.getData(amount = amount, category = categoryId)
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