package com.navent.entertainmentcompse.service


import com.navent.entertainmentcompse.model.CategoryTrivia
import com.navent.entertainmentcompse.model.Response
import com.navent.entertainmentcompse.model.TriviaQuestion
import com.navent.entertainmentcompse.model.TriviaResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiGame {

    @GET("/api_category.php")
    suspend fun getCategories(): CategoryTrivia

    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int,
        @Query("category") category: String,
        @Query("difficulty") difficulty: String,
        @Query("type") type: String
    ): TriviaResponse

    @GET("api.php")
    suspend fun getData(
        @Query("amount") amount: String,
        @Query("category") category: Int
    ): retrofit2.Response<TriviaResponse>

}