package com.raksandroid.endlessrecyclerview.repository


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BaseRespository {

   fun <T> addToEnqueue(baseCall: Call<T>, listener: NetRequestListenerNew, ReqType: Int, vararg TokenReq: Int) {
        baseCall.enqueue(object : Callback<T>{
            override fun onFailure(call: Call<T>, t: Throwable) {
                listener.onReceiveError(ReqType,t.message.toString(),apiurl = "")
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful){
                    listener.onReceiveResult(ReqType, response,"")
                }
            }
        })
    }
}

