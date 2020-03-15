package com.raksandroid.endlessrecyclerview.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raksandroid.endlessrecyclerview.utils.Retroconnect
import com.raksandroid.endlessrecyclerview.model.api.RaksApi
import com.raksandroid.endlessrecyclerview.model.entity.CommonResult
import com.raksandroid.endlessrecyclerview.repository.BaseRespository
import com.raksandroid.endlessrecyclerview.repository.NetRequestListenerNew
import retrofit2.Response

class RecyclerViewmodel:ViewModel(), NetRequestListenerNew {

    var startIndex = 0
    var endIndex = 20
    private var retroApiCallNode = Retroconnect().instance.retrofitNode()?.create(RaksApi::class.java)


    private var citiesList = MutableLiveData<CommonResult>()
    private var error = MutableLiveData<String>()

    private val baseRepository by lazy {
        BaseRespository()
    }
    override fun onReceiveResult(ReqType: Int, response: Response<*>, apiurl: String) {
        when(ReqType){
            1 -> {
                val responses = Retroconnect().instance.dataConvertor(response,CommonResult::class.java)
                citiesList.value = responses
            }
        }
    }

    override fun onReceiveError(ReqType: Int, Error: String, apiurl: String) {
        error.value = Error
    }

    fun myCitiesList(){
        val params = HashMap<String,Int>()
        params.put("startindex",startIndex)
        params.put("endindex",endIndex)
        val apiCall = retroApiCallNode?.citiesCallApi(params)
        apiCall?.let {
            baseRepository.addToEnqueue(it,this,1)
        }
    }

    fun getCitiesList():MutableLiveData<CommonResult>{
        return citiesList
    }

    fun getError():MutableLiveData<String>{
        return error
    }
}