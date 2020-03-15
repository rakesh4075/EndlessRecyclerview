package com.raksandroid.endlessrecyclerview.utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.raksandroid.endlessrecyclerview.R
import com.raksandroid.endlessrecyclerview.model.entity.SectionOrRow

class EndlessRecyclerAdapter(private val mData:ArrayList<SectionOrRow>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==0){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.loading,parent,false)
            return SectionViewHolder(
                view
            )
        }else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
            return RowViewHolder(
                view
            )
        }
    }


    override fun getItemViewType(position: Int): Int {
        val item = mData[position]
        return if (!item.isRow()) {
            0
        } else {
            1
        }
    }



    override fun getItemCount(): Int {
        return mData.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RowViewHolder){
            holder.txt.text= "${mData[position].row} $position"
        }
    }

    class SectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    class RowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txt = view.findViewById<TextView>(R.id.sampleText)
    }
}


