package com.abel.whatsup;


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

import org.parceler.Parcels;

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

    private Article article;

    public NewsDetailFragment() {
        // Required empty public constructor
    }
//
    public static  NewsDetailFragment newInstance(Article article) {
        NewsDetailFragment newsDetailFragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("news", Parcels.wrap(article));
        newsDetailFragment.setArguments(args);
        return newsDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        article = Parcels.unwrap(getArguments().getParcelable("news"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_news_detail, container, false);
        ButterKnife.bind(this, view);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.icon_news)
                .error(R.drawable.icon_news);

        Glide.with(this).load(article.getUrlToImage()).apply(options).into(mImageLabel);

        mAuthor.setText(article.getAuthor());
        mDescription.setText(article.getDescription());
        mPublishedAt.setText(Utils.DateFormat(article.getPublishedAt()));

        return view;
    }

}
