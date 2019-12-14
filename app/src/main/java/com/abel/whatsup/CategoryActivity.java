package com.abel.whatsup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.newsBtn) Button mNewsBtn;
    @BindView(R.id.wBtn) Button mWeatherBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ButterKnife.bind(this);

        mNewsBtn.setOnClickListener(this);
        mWeatherBtn.setOnClickListener(this);
    }
        @Override
        public void onClick(View v){
            if (v == mNewsBtn) {
                Intent intent = new Intent(CategoryActivity.this, NewsActivity.class);
                startActivity(intent);

            }else if(v == mWeatherBtn){
                Intent intent = new Intent(CategoryActivity.this, WeatherActivity.class);
                startActivity(intent);
            }
        }
    }


