package com.raksandroid.endlessrecyclerview.utils

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Retroconnect {

     val instance: Retroconnect by lazy {
         Retroconnect()
    }
    private val loggingInterceptor by lazy { HttpLoggingInterceptor() }
    var httpClient = OkHttpClient.Builder()

    init {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(loggingInterceptor)
    }

    private val httpsNodeBasePath = "http://lovdatt.herokuapp.com"
  //private val httpsNodeBasePath = "http://192.168.43.495:3000"
    internal var retryCalled = ArrayList<Int>()
    var mNodeRetrofit: Retrofit? = null
    fun retrofitNode(): Retrofit?{
        mNodeRetrofit = Retrofit.Builder()
            .baseUrl(httpsNodeBasePath)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
        return mNodeRetrofit
    }



    fun <T> dataConvertor(response: retrofit2.Response<*>, cls: Class<T>): T? {
        val cmmLoginParser: T?
        val body = response.body()
        cmmLoginParser = if (body is String) {
            Gson().fromJson(body.run { toString() }, cls)

        } else {
            (response as retrofit2.Response<T>).body()
        }
        return cmmLoginParser
    }


}