package com.abel.whatsup.adapters;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abel.whatsup.NewsDetailFragment;
import com.abel.whatsup.R;
import com.abel.whatsup.models.Article;

import java.util.List;

public class NewsPagerAdapter extends FragmentPagerAdapter {

    private List<Article> articles;

    public NewsPagerAdapter(@NonNull FragmentManager fm, int behavior, List<Article> article) {
        super(fm, behavior);
        articles = article;
    }

    @Override
    public Fragment getItem(int position) {
        return NewsDetailFragment.newInstance(articles.get(position));
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return articles.get(position).getTitle();
    }


}
