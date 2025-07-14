package com.navent.entertainmentcompse.service


import com.navent.entertainmentcompse.model.CategoryTrivia
import com.navent.entertainmentcompse.model.Response
import com.navent.entertainmentcompse.model.TriviaQuestion
import com.navent.entertainmentcompse.model.TriviaResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiGame {

    @GET("/api.php/{amount}/{category}")
    suspend fun getData(@Query("amount") amount: String?,
                        @Query("category") category: String?
                        ): Response

    @GET("/api_category.php")
    suspend fun getCategories(): CategoryTrivia

    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int,
        @Query("category") category: String,
        @Query("difficulty") difficulty: String,
        @Query("type") type: String
    ): TriviaResponse


/*
 @GET("/api.php")
    suspend fun getData(@Query("amount") amount: String?): Response
 */

//    https://opentdb.com/api.php?amount=10&category=9

    /*
     @GET("installments")
    suspend fun getInstallments(
        @Query("amount") amount: String,
        @Query("payment_method_id") methodId: String,
        @Query("issuer.id") issuer: String,
    ): List<Installment>
     */

    /*
    @POST("v2/leads/{lead_type_id}/{posting_id}")
fun generateLeadWspOrCall(
        @Path("lead_type_id") leadType: LeadType,
        @Path("posting_id") postingId: String,
        @Query("email") email: String? = null): MutableLiveData<WSResponse<BSRELeadMessage>>
     */


}