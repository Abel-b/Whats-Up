package com.abel.whatsup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abel.whatsup.adapters.FirebaseNewsListAdapter;
import com.abel.whatsup.adapters.FirebaseNewsViewHolder;
import com.abel.whatsup.models.Article;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;




import butterknife.BindView;
import butterknife.ButterKnife;

public class LikedNewsActivity extends AppCompatActivity implements OnStartDragListener {

    private DatabaseReference mNewsReference;
    private FirebaseRecyclerAdapter<Article, FirebaseNewsViewHolder> mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;
    
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_later_news);

        ButterKnife.bind(this);

        setUpFirebaseAdapter();
    }
    private void setUpFirebaseAdapter(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        mNewsReference = FirebaseDatabase.getInstance().getReference(NewsDetailFragment.FIREBASE_CHILD_NEWS).child(uid);
        FirebaseRecyclerOptions<Article> options =
                new FirebaseRecyclerOptions.Builder<Article>()
                        .setQuery(mNewsReference, Article.class)
                        .build();

        mFirebaseAdapter = new FirebaseNewsListAdapter(options, mNewsReference,this, this);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
        mRecyclerView.setHasFixedSize(true);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback((ItemTouchHelperAdapter) mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mFirebaseAdapter!= null) {
            mFirebaseAdapter.stopListening();
        }
    }
    public void onStartDrag(RecyclerView.ViewHolder viewHolder){
        hideProgressBar();
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

}

