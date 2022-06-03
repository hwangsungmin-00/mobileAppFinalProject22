package com.example.finalproject

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("3510500/meal_card_merchant/getList")
    fun getList(
        @Query("serviceKey") apiKey:String?,
        @Query("pageNo") page:Int,
        @Query("numOfRows") pageSize:Int,
        @Query("type") type:String?
    ) : Call<PageListModel>
/*
    @GET("B551182/pubReliefHospService/getpubReliefHospList")
    fun getXmlList(
        @Query("serviceKey") apiKey:String?,
        @Query("pageNo") page:Int,
        @Query("numOfRows") pageSize:Int
    ): Call<responseInfo>

 */
}