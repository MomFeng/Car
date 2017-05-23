package com.hncst.administrator.car.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.administrator.car.R
import com.hncst.administrator.car.Interface.BindLayout
import com.hncst.administrator.car.Interface.BindView
import com.hncst.administrator.car.Interface.BindonClick
import com.hncst.administrator.car.Interface.MyActivity

/**
 * Language:Kotlin
 * 用来写用户评论的activity
 * Created by MomFeng on 2017/5/23 0023.
 */
@BindLayout(R.layout.activity_comment)
class CommentActivity : MyActivity() {

    @BindView(R.id.btn_comment_back)
    private var btn_comment_back : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @BindonClick(R.id.btn_comment_back)
    fun myonclick(v : View){
        when(v.id){
            R.id.btn_comment_back -> {
                this@CommentActivity.finish()
            }
        }
    }
}