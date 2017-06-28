package com.yangkunjian.svgdemo.activity;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.yangkunjian.svgdemo.R;

public class MainActivity extends AppCompatActivity {

    ImageView ivPendulum1, ivPendulum2, ivSunEarthMoon, ivDemo2, ivBack, ivSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivPendulum1 = (ImageView) findViewById(R.id.ivPendulum1);
        restart(ivPendulum1.getDrawable());

        ivSunEarthMoon = (ImageView) findViewById(R.id.ivSunEarthMoon);
        restart(ivSunEarthMoon.getDrawable());

        ivDemo2 = (ImageView) findViewById(R.id.ivDemo2);
        ivDemo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restart(ivDemo2.getDrawable());
            }
        });

        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restart(ivBack.getDrawable());
            }
        });
        ivSearch = (ImageView) findViewById(R.id.ivSearch);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restart(ivSearch.getDrawable());
            }
        });
    }

    private void restart(Drawable drawable) {
        if (drawable instanceof Animatable) {
            Animatable animatable = (Animatable) drawable;
            if (animatable.isRunning()) animatable.stop();
            animatable.start();
        }
    }

    private void release(Drawable drawable) {
        if (drawable instanceof Animatable) {
            Animatable animatable = (Animatable) drawable;
            if (animatable != null && animatable.isRunning())
                animatable.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release(ivPendulum1.getDrawable());
        release(ivSunEarthMoon.getDrawable());
    }

    public void svgForJava(View v) {
        Intent intent = new Intent(this, SvgForJavaActivity.class);
        startActivity(intent);
    }
}
