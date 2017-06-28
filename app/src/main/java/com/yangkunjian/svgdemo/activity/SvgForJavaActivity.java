package com.yangkunjian.svgdemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yangkunjian.svgdemo.R;
import com.yangkunjian.svgdemo.custom.AnimatedSvgView;
import com.yangkunjian.svgdemo.custom.SVG;
import com.yangkunjian.svgdemo.custom.SvgView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SvgForJavaActivity extends AppCompatActivity {

    @Bind(R.id.svgView1)
    SvgView svgView;
    String pathDate = "m191.955994,172.473007c145.697021,-37.820007 -82.961884,-12.071014 88.610016,-77.323006c171.571991,-65.251301 9.738983,52.956993 88.609985,77.323006c78.87204,24.366989 -131.034973,163.827988 -88.609985,77.32399c42.425995,-86.50499 -234.305817,-39.502991 -88.610016,-77.32399z";


    @Bind(R.id.svg_view_0)
    AnimatedSvgView svgView0;
    @Bind(R.id.svg_view_1)
    AnimatedSvgView svgView1;
    @Bind(R.id.svg_view_2)
    AnimatedSvgView svgView2;
    @Bind(R.id.svg_view_3)
    AnimatedSvgView svgView3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg_for_java);
        ButterKnife.bind(this);

        svgView.setPathData(pathDate);

        svgView0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSvg(svgView0, SVG.values()[0]);
            }
        });
        svgView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSvg(svgView1, SVG.values()[1]);
            }
        });
        svgView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSvg(svgView2, SVG.values()[2]);
            }
        });
        svgView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSvg(svgView3, SVG.values()[3]);
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setSvg(svgView0, SVG.values()[0]);
                setSvg(svgView1, SVG.values()[1]);
                setSvg(svgView2, SVG.values()[2]);
                setSvg(svgView3, SVG.values()[3]);
            }
        }, 500);

    }

    private void setSvg(AnimatedSvgView svgView, SVG svg) {
        svgView.setGlyphStrings(svg.glyphs);
        svgView.setFillColors(svg.colors);
        svgView.setViewportSize(svg.width, svg.height);
        svgView.setTraceResidueColor(0x32000000);
        svgView.setTraceColors(svg.colors);
        svgView.rebuildGlyphData();
        svgView.start();
    }
}
