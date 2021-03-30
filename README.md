# example-mvvmkotlin
Example with MVVM, DataBinding With LiveData

This is very simple example

Architecture:

![image](https://github.com/winniecake/example-mvvmkotlin/blob/master/architecture.png?raw=true)


### Step1: Adding DataBinding and Implementations in your Gradle File:
```JAVA
android {
 dataBinding {
        enabled = true
    }
}
dependencies {
    implementation "androidx.lifecycle:lifecycle-extensions:2.2.0"
}
```

### Step2: Create a new class for the Model
```JAVA
class MyData(
        var no: String,
        var name: String
)

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
```

### Step3: Create a new class for the ViewModel
```JAVA
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
```

### Step4: Create the View class
```JAVA
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // 取得ViewModel
        val mainViewModel: MainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.mainViewModel = mainViewModel
        binding.lifecycleOwner = this

        val adapter = DataListAdapter(ArrayList())
        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.adapter = adapter

        // 觀察者模式
        mainViewModel.mData.observe(this, androidx.lifecycle.Observer { dataList ->
            adapter.updateList(dataList as ArrayList<MyData>)
            binding.list.visibility = View.VISIBLE
        })

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
```

### Step5: XML file
```XML
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

        <import type="android.view.View" />

        <variable
            name="mainViewModel"
            type="com.winniecake.mvvmkotlin.viewmodel.MainViewModel" />

    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context="com.winniecake.mvvmkotlin.view.MainActivity">

        <Button
            android:id="@+id/btnStart"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:enabled="@{mainViewModel.mIsLoading ? false : true}"
            android:onClick="@{() -> mainViewModel.requestData()}"
            android:text="Start"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <ProgressBar
            android:id="@+id/progressBar"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:layout_constraintBottom_toBottomOf="@+id/btnStart"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/list"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:layout_constraintBottom_toBottomOf="@+id/btnStart"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"/>

    </androidx.constraintlayout.widget.ConstraintLayout>

</layout>
```

