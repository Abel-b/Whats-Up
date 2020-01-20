package com.abel.whatsup;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abel.whatsup.models.Article;
import com.abel.whatsup.network.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsDetailFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.img)
    ImageView mImageLabel;
    @BindView(R.id.author)
    TextView mAuthor;
    @BindView(R.id.desc) TextView mDescription;
    @BindView(R.id.publishedAt) TextView mPublishedAt;
    @BindView(R.id.likeButton)
    Button mLikedNewsButton;
    @BindView(R.id.url) TextView mUrl;

    private Article article;
    public static final String FIREBASE_CHILD_NEWS = "likedNews";

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
        mDescription.setText(article.getContent());
        mPublishedAt.setText(Utils.DateFormat(article.getPublishedAt()));
        mUrl.setText("Read more: " + article.getUrl());


        mLikedNewsButton.setOnClickListener(this);
        mUrl.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == mLikedNewsButton){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference newsRef = FirebaseDatabase.getInstance().getReference(FIREBASE_CHILD_NEWS).child(uid);

            DatabaseReference pushRef = newsRef.push();
            String pushId = pushRef.getKey();
            article.setPushId(pushId);
            pushRef.setValue(article);
            mLikedNewsButton.setEnabled(false);

            Toast.makeText(getContext(), "Added to Watch later", Toast.LENGTH_LONG).show();
            mLikedNewsButton.setCompoundDrawables(Drawable.createFromPath("@drawable/ic_action_like"), Drawable.createFromPath("@drawable/ic_action_like"),Drawable.createFromPath("@drawable/ic_action_like"), Drawable.createFromPath("@drawable/ic_action_like"));
            Log.i("CHANGE BUTTON", "CHANGED!!!!");
        }
        if (v == mUrl){
            String url = article.getUrl();
            Intent intent = new Intent(getContext(), NewsActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
        }

    }

}
