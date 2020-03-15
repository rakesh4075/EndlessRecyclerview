package com.raksandroid.endlessrecyclerview.repository

import androidx.lifecycle.MutableLiveData
import retrofit2.Response

interface NetRequestListenerNew {
    fun onReceiveResult(ReqType: Int, response: Response<*>, apiurl: String)
    fun onReceiveError(ReqType: Int, Error: String, apiurl: String)

}
