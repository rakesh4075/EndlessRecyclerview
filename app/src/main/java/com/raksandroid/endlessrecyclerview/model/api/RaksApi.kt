package com.raksandroid.endlessrecyclerview.model.api


import com.raksandroid.endlessrecyclerview.model.entity.CommonResult
import retrofit2.Call
import retrofit2.http.*
import kotlin.collections.HashMap


interface RaksApi {


   /* @POST("/initialfetchapi")
    fun initialfetch(): Call<DynamicValuParser>


    @FormUrlEncoded
    @POST("/mutuallike")
    fun mutuallikeApi(@FieldMap fields: HashMap<String, String>): Call<ViewListParser>*/


    @FormUrlEncoded
    @POST("endlessrecycler/cities")
    fun citiesCallApi(@FieldMap fields: HashMap<String, Int>): Call<CommonResult>

}