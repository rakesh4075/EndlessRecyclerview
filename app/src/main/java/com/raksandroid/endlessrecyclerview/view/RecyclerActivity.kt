package com.raksandroid.endlessrecyclerview.view

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.raksandroid.endlessrecyclerview.databinding.RecyclerActivityBinding
import com.raksandroid.endlessrecyclerview.model.entity.CommonResult
import com.raksandroid.endlessrecyclerview.model.entity.SectionOrRow
import com.raksandroid.endlessrecyclerview.utils.EndlessRecyclerAdapter
import com.raksandroid.endlessrecyclerview.utils.EndlessRecyclerViewScrollListener
import com.raksandroid.endlessrecyclerview.viewmodel.RecyclerViewmodel
import kotlin.math.log

class RecyclerActivity : AppCompatActivity() {
    private lateinit var sectionOrRow: SectionOrRow
    private lateinit var viewmodel: RecyclerViewmodel
    private lateinit var binding: RecyclerActivityBinding
    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var adapter: EndlessRecyclerAdapter
    private var mDataList = ArrayList<SectionOrRow>()
    private var isLoading = false
    private var pageNo =1
    private var isLoadedAllItems = false
    private var previousCount = 0
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private var TotalResult = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RecyclerActivityBinding.inflate(layoutInflater)
        viewmodel = ViewModelProvider(this).get(RecyclerViewmodel::class.java)
        setContentView(binding.root)

        setAdapter()
        observeViewmodel()
        viewmodel.myCitiesList()


    }

    private fun observeViewmodel() {
        with(viewmodel){
            getCitiesList().observe(this@RecyclerActivity, Observer {

                initData(it)
            })
        }
    }

    private fun initData(response: CommonResult?) {
        if (response?.RESPONSECODE==1 && response.ERRORCODE==0){
            binding.progressBar.visibility = View.GONE
            if (pageNo>1){
                removeLoading()
            }else{
                mDataList.clear()
                isLoadedAllItems = false
            }
            previousCount = response.CITIES.size
            Log.d("@@@@@","previous $previousCount")
            TotalResult = response.ToTOLRESULT
            for (i in response.CITIES){
                sectionOrRow = SectionOrRow.createRow(i)
                mDataList.addAll(listOf(sectionOrRow))
            }
            adapter.notifyDataSetChanged()
            scrollListener.setLoaded()
        }
    }

    private fun setAdapter() {
        binding.recycler.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.recycler.layoutManager = mLayoutManager
        adapter = EndlessRecyclerAdapter(mDataList)
        binding.recycler.adapter = adapter

        scrollListener = EndlessRecyclerViewScrollListener(mLayoutManager)
        scrollListener.setOnLoadMoreListener(object :EndlessRecyclerViewScrollListener.OnLoadMoreListener{
            override fun onLoadMore() {
                loadMoreData()
            }
        })
        binding.recycler.addOnScrollListener(scrollListener)
    }

    private fun loadMoreData() {
        if (!isLoadedAllItems){
            pageNo++
            viewmodel.startIndex = viewmodel.startIndex+previousCount
            viewmodel.endIndex = viewmodel.startIndex+20
            if (viewmodel.startIndex!=TotalResult){
                viewmodel.myCitiesList()
                addProgressItem()
            }



        }
    }

    private fun addProgressItem() {
        val handler = Handler()
        handler.post {
            sectionOrRow = SectionOrRow.createSection("progress")
            mDataList.add(sectionOrRow)
            adapter.notifyItemInserted(mDataList.size-1)
        }
    }

    private fun removeLoading(){
        mDataList.removeAt(mDataList.size - 1)
        adapter.notifyItemRemoved(mDataList.size-1)
    }

}
