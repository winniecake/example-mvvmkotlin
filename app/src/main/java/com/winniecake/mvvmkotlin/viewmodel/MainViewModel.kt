package com.winniecake.mvvmkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.winniecake.mvvmkotlin.model.MainRepository
import com.winniecake.mvvmkotlin.model.MyData

/**
 * 負責接收view傳來的任何需求, 跟Repository要求資料
 * ViewModel不會被View事件影響, ex:Activity被回收或螢幕旋轉等不會造成ViewModel改變
 * ViewModel不能有views的reference, 否則會memory leak
 * 用context要拿ApplicationContext避免memory leak
 */
class MainViewModel : ViewModel() {
    val mData = MutableLiveData<List<MyData>>()
    val mIsLoading = MutableLiveData<Boolean>(false)

    private var mRepository = MainRepository()

    fun requestData() {
        mIsLoading.value = true
        mRepository.requestData { dataList ->
            mData.value = dataList
            mIsLoading.setValue(false)
        }
    }
}