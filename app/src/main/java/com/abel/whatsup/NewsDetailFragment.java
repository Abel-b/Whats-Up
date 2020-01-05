package com.abel.whatsup;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abel.whatsup.models.Article;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsDetailFragment extends Fragment {

    @BindView(R.id.img)
    ImageView mImageLabel;
    @BindView(R.id.author)
    TextView mAuthor;
    @BindView(R.id.desc) TextView mDescription;
    @BindView(R.id.publishedAt) TextView mPublishedAt;

    private Article articles;

    public NewsDetailFragment() {
        // Required empty public constructor
    }

    public static  NewsDetailFragment newInstance(Article articles) {
        NewsDetailFragment newsDetailFragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("news", Parcels.wrap(articles));
        newsDetailFragment.setArguments(args);
        return newsDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        articles = Parcels.unwrap(getArguments().getParcelable("news"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_news_detail, container, false);
        ButterKnife.bind(this, view);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        Glide.with(this).load(articles.getUrlToImage()).apply(options).into(mImageLabel);

        mAuthor.setText(articles.getAuthor());
        mDescription.setText(articles.getDescription());
        mPublishedAt.setText(articles.getPublishedAt());

        return view;
    }

}
