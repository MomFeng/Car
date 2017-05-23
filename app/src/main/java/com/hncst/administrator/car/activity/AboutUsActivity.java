package com.hncst.administrator.car.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hncst.administrator.car.Interface.BindLayout;
import com.hncst.administrator.car.Interface.BindView;
import com.hncst.administrator.car.Interface.BindonClick;
import com.hncst.administrator.car.Interface.MyActivity;
import com.example.administrator.car.R;

/**
 * 关于我们
 * Language:JAVA
 * Created by MomFeng on 2017/5/17 0017.
 *
 *----------Dragon be here!----------/
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑
 * 　　　　┃　　　┃代码无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━by:coder-pig
 */
@BindLayout(R.layout.activity_aboutus)
public class AboutUsActivity extends MyActivity {

    @BindView(R.id.btn_aboutus_back)
    Button btn_aboutus_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @BindonClick({R.id.btn_aboutus_back})
    public void myonclick(View v) {
        switch (v.getId()) {
            case R.id.btn_aboutus_back:
                AboutUsActivity.this.finish();
                break;
        }
    }

}
