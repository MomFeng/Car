package com.hncst.administrator.car.activity

import android.os.Bundle

import com.example.administrator.car.R
import com.hncst.administrator.car.Interface.MyActivity

/**
 * Language:Kotlin
 * 目前是Kotlin的测试activity
 */
class KotlinActivity : MyActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
    }
}
