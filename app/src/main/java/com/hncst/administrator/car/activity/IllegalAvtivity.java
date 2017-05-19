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
 * 违章查询
 * 79cb00054f112aacf3cc63b0faf708d9
 * http://v.juhe.cn/wz/citys?province=JS&key=79cb00054f112aacf3cc63b0faf708d9
 * Created by MomFeng on 2017/5/18 0018.
 */
@BindLayout(R.layout.activity_illegal)
public class IllegalAvtivity extends MyActivity{

    @BindView(R.id.btn_illegal_back)
    private Button btn_illegal_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @BindonClick({R.id.btn_illegal_back})
    public void myonclick(View v){
        switch(v.getId()){
            case R.id.btn_illegal_back:
                IllegalAvtivity.this.finish();
                break;
        }
    }

}
