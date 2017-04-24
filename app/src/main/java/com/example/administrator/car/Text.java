package com.example.administrator.car;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

public class Text extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private CarView sur_carview, sur_carview1;
    private SeekBar sbar_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        sur_carview = (CarView) findViewById(R.id.sur_carview);
        sur_carview1 = (CarView) findViewById(R.id.sur_carview1);
        sbar_bar = (SeekBar) findViewById(R.id.sbar_bar);

        /*ViewGroup.LayoutParams lp = sur_carview.getLayoutParams();
        lp.width = 1080;
        lp.height = 1200;
        sur_carview.getHolder().setFixedSize(1080, 1200);
        sur_carview.setLayoutParams(lp);*/

        sbar_bar.setMax(240);
        sbar_bar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        sur_carview.setSpeed(progress);
        sur_carview1.setSpeed(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
