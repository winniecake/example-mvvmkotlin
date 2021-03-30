package com.winniecake.mvvmkotlin.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.winniecake.mvvmkotlin.R
import com.winniecake.mvvmkotlin.model.MyData
import java.util.*

class DataListAdapter(private val mDataList: ArrayList<MyData>) : RecyclerView.Adapter<DataListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mTextNo.text = mDataList[position].no
        holder.mTextName.text = mDataList[position].name
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    fun updateList(dataList: ArrayList<MyData>) {
        mDataList.clear()
        mDataList.addAll(dataList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mTextNo: TextView = itemView.findViewById(R.id.no)
        var mTextName: TextView = itemView.findViewById(R.id.name)
    }
}