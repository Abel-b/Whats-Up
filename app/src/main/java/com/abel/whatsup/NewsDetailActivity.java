package com.abel.whatsup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.abel.whatsup.adapters.NewsPagerAdapter;
import com.abel.whatsup.models.Article;
import org.parceler.Parcels;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailActivity extends AppCompatActivity {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private NewsPagerAdapter adapterViewPager;
    List<Article> articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);

        articles = Parcels.unwrap(getIntent().getParcelableExtra("news"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new NewsPagerAdapter(getSupportFragmentManager(), 1, articles);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);

    }
}
