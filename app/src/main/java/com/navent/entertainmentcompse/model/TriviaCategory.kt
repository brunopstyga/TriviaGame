package com.navent.entertainmentcompse.model
import com.google.gson.annotations.SerializedName

data class CategoryTrivia(
    @SerializedName("trivia_categories")
    val triviaCategory:List<Category>
)

data class TriviaResponse(
    @SerializedName("results")
    val questions: List<TriviaQuestion>
)

data class Category(
    val id: Int,
    val name: String?
)

data class TriviaQuestion(
    val id: Int,
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    val createdBy: String,
    @SerializedName("correct_answer")
    val correctAnswer: String,
    @SerializedName("incorrect_answers")
    val incorrectAnswers: List<String>
)

data class TriviaQueryParams(
    val amount: Int = 10,
    val category: Int,
    val type: String = "multiple"
)

data class Response(
    @SerializedName("response_code")
    val responseCode: String,
    val results: List<TriviaQuestion>
)