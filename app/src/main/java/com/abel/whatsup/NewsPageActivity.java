package com.abel.whatsup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abel.whatsup.adapters.NewsListAdapter;
import com.abel.whatsup.models.Article;
import com.abel.whatsup.models.CountryNewsResponse;
import com.abel.whatsup.network.NewsApi;
import com.abel.whatsup.network.NewsClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.abel.whatsup.network.Constants.NEWS_API_KEY;

public class NewsPageActivity extends AppCompatActivity {

    @BindView
            (R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.errorTextView)
    TextView mErrorTextView;

    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private NewsListAdapter adapter;
    public static final String TAG = NewsPageActivity.class.getSimpleName();

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_page);

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    getSupportActionBar().setTitle("Welcome, " + user.getDisplayName() + "!");
                }else {

                }
            }
        };

        NewsApi client = NewsClient.getNewsClient();

        Call<CountryNewsResponse> call = client.getNews("US", NEWS_API_KEY);

        call.enqueue(new Callback<CountryNewsResponse>() {
            @Override
            public void onResponse(Call<CountryNewsResponse> call, Response<CountryNewsResponse> response) {

                hideProgressBar();
                if (response.isSuccessful()) {
                    Log.i("START IF", "BEGINNING");
                    articles = response.body().getArticles();
                    adapter = new NewsListAdapter(articles, NewsPageActivity.this);
                    mRecyclerView.setAdapter(adapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NewsPageActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);
                    Log.i("END IF", "LAST IF ");

                } else {
                    showFailureMessage();
                    Log.i("ELSE", "else tag");
                }
            }

            @Override
            public void onFailure(Call<CountryNewsResponse> call, Throwable t) {
                Log.i("FAILURE", "on failure");
            }
        });
    }

    private void showFailureMessage() {
        mErrorTextView.setText("Bruh, some bug is bothering! Give me one more chance to fix your relation");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_logout){
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(NewsPageActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if (mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}

//    public void loadNews(){
//
//        NewsApi newsApi = NewsClient.getNewsClient().create(NewsApi.class);
//        String country = Utils.getCountry();
//
//        Call<CountryNewsResponse> call = newsApi.getNews(country, NEWS_API_KEY);
//
//        call.enqueue(new Callback<CountryNewsResponse>() {
//            @Override
//            public void onResponse(Call<CountryNewsResponse> call, Response<CountryNewsResponse> response) {
//                if (response.isSuccessful() && response.body().getArticles() != null){
//                    if(!articles.isEmpty()){
//                        articles.clear();
//                    }
//                    articles = response.body().getArticles();
//                    adapter = new NewsListAdapter(articles, NewsPageActivity.this);
//                    mRecyclerView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//                }else {
//                    Toast.makeText(NewsPageActivity.this, "No Result!", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CountryNewsResponse> call, Throwable t) {
//
//            }
//        });
//    }
//}
