package com.example.administrator.car.activity.glide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.car.R;
import com.example.administrator.car.activity.MainActivity;
import com.example.administrator.car.activity.StartActivity;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public class ThreeFragment extends Fragment implements View.OnClickListener {

    private Button btn_start;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmentglide_three , null);
        btn_start = (Button) v.findViewById(R.id.btn_start);

        btn_start.setOnClickListener(this);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp =getActivity().getSharedPreferences("config", StartActivity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isfirst" , false);
                editor.commit();

                Intent i = new Intent(getActivity() , MainActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        return v;
    }

    @Override
    public void onClick(View v) {

    }
}
