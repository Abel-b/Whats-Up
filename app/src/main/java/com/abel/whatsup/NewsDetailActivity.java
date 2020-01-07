package com.abel.whatsup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.abel.whatsup.adapters.NewsPagerAdapter;
import com.abel.whatsup.models.Article;
import org.parceler.Parcels;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailActivity extends AppCompatActivity {

//    @BindView(R.id.viewPager)
//    ViewPager mViewPager;
    private NewsPagerAdapter adapterViewPager;
    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);

        article = Parcels.unwrap(getIntent().getParcelableExtra("news"));
        int startingPosition = getIntent().getIntExtra("position", 0);
        Toast.makeText(this,"Source: " + article.getSource().getName(), Toast.LENGTH_LONG).show();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        NewsDetailFragment newsDetailFragment = new NewsDetailFragment();
        Bundle args= new Bundle();

        args.putParcelable("news", Parcels.wrap(article));
        newsDetailFragment.setArguments(args);
        fragmentTransaction.add(R.id.newsFragment, newsDetailFragment);
        fragmentTransaction.commit();



//        fragmentTransaction.addToBackStack()
//        adapterViewPager = new NewsPagerAdapter(getSupportFragmentManager(), 1, articles);
//        mViewPager.setAdapter(adapterViewPager);
//        mViewPager.setCurrentItem(startingPosition);

    }
}
