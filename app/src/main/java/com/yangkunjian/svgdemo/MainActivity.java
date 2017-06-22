package com.yangkunjian.svgdemo;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView ivLoading, ivSunEarthMoon, ivDemo2, ivBack, ivSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivLoading = (ImageView) findViewById(R.id.ivLoading);
        restart((Animatable) ivLoading.getDrawable());

        ivSunEarthMoon = (ImageView) findViewById(R.id.ivSunEarthMoon);
        restart((Animatable) ivSunEarthMoon.getDrawable());

        ivDemo2 = (ImageView) findViewById(R.id.ivDemo2);
        ivDemo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restart((Animatable) ivDemo2.getDrawable());
            }
        });

        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restart((Animatable) ivBack.getDrawable());
            }
        });
        ivSearch = (ImageView) findViewById(R.id.ivSearch);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restart((Animatable) ivSearch.getDrawable());
            }
        });
    }

    private void restart(Animatable animatable) {
        if (animatable.isRunning()) animatable.stop();
        animatable.start();
    }

    private void release(Animatable animatable) {
        if (animatable != null && animatable.isRunning())
            animatable.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release((Animatable) ivLoading.getDrawable());
        release((Animatable) ivSunEarthMoon.getDrawable());
    }
}
