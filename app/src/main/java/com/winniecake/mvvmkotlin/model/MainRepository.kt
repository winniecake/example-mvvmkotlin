package com.winniecake.mvvmkotlin.model

import android.os.Handler
import android.os.Looper
import java.util.*

/**
 * 取得來源資料
 */
class MainRepository {
    fun requestData(listener: (ArrayList<MyData>) -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            val dataList = ArrayList<MyData>()
            dataList.add(MyData("1", "apple"))
            dataList.add(MyData("2", "orange"))
            dataList.add(MyData("3", "strawberry"))
            listener(dataList)
        }, 2000)
    }
}