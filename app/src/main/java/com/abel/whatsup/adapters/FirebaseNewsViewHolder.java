package com.abel.whatsup.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abel.whatsup.NewsDetailActivity;
import com.abel.whatsup.NewsDetailFragment;
import com.abel.whatsup.OnStartDragListener;
import com.abel.whatsup.R;
import com.abel.whatsup.Utils;
import com.abel.whatsup.models.Article;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View mView;
    Context mContext;
    public ImageView mNewsImageView;

    public FirebaseNewsViewHolder(View itemView){
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }


    public void bindNews(Article article){

        mNewsImageView = mView.findViewById(R.id.img);
        TextView titleTextView = mView.findViewById(R.id.title);
        TextView descTextView = mView.findViewById(R.id.desc);
        TextView dateTextView = mView.findViewById(R.id.publishedAt);
        TextView urlTextView = mView.findViewById(R.id.url);

        titleTextView.setText(article.getTitle());
        descTextView.setText(article.getDescription());
        dateTextView.setText(Utils.DateFormat(article.getPublishedAt()));
        urlTextView.setText(article.getUrl());
        Picasso.get().load(article.getUrlToImage()).into(mNewsImageView);
    }

    @Override
    public void onClick(View v) {
        final ArrayList<Article> articles = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(NewsDetailFragment.FIREBASE_CHILD_NEWS).child(uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    articles.add(snapshot.getValue(Article.class));
                }

                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("news", Parcels.wrap(articles));

                mContext.startActivity(intent);


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
