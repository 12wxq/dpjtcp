package com.example.myapplication;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public class cwbkActivity extends AppCompatActivity {
    int[] image=new int[]{
            R.drawable.f1,
            R.drawable.f2,R.drawable.f3, R.drawable.f4

    };


    private AdapterViewFlipper flipper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cwbk);
        findViewById(R.id.tz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(cwbkActivity.this,MoreActivity.class);
                startActivity(intent1);
            }
        });
        flipper=(AdapterViewFlipper)findViewById(R.id.flipper);
        BaseAdapter adapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return image.length;
            }

            @Override
            public Object getItem(int position) {

                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View converView, ViewGroup parent) {
                ImageView imageView=new ImageView(cwbkActivity.this);

                imageView.setImageResource(image[position]);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                return  imageView;
            }
        };
        flipper.setAdapter(adapter);
    }


    public void prev(View source){


        flipper.showPrevious();
        flipper.stopFlipping();;
    }
    public void next(View source){
        flipper.showNext();
        flipper.stopFlipping();
    }


}