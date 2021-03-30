package com.winniecake.mvvmkotlin.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.winniecake.mvvmkotlin.R
import com.winniecake.mvvmkotlin.databinding.ActivityMainBinding
import com.winniecake.mvvmkotlin.model.MyData
import com.winniecake.mvvmkotlin.viewmodel.MainViewModel
import java.util.*

/**
 *
 * View -> ViewModel -> Repository
 *                   <-
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // 取得ViewModel,生命週期會持續到 MainActivity destroy, MainActivity還在活動ViewModel不會被清除
        val mainViewModel: MainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.mainViewModel = mainViewModel
        binding.lifecycleOwner = this

        val adapter = DataListAdapter(ArrayList())
        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.adapter = adapter

        // 觀察者模式
        // 非同步資料取得/處理mData後, 會callback來完成UI處理
        mainViewModel.mData.observe(this, androidx.lifecycle.Observer { dataList ->
            adapter.updateList(dataList as ArrayList<MyData>)
            binding.list.visibility = View.VISIBLE
        })

        // 非同步資料取得/處理mIsLoading後, 會callback來完成UI處理
        mainViewModel.mIsLoading.observe(this, androidx.lifecycle.Observer { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.list.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
    }
}