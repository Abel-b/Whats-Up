package com.abel.whatsup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abel.whatsup.adapters.NewsListAdapter;
import com.abel.whatsup.models.Article;
import com.abel.whatsup.models.CountryNewsResponse;
import com.abel.whatsup.network.NewsApi;
import com.abel.whatsup.network.NewsClient;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.abel.whatsup.network.Constants.NEWS_API_KEY;

public class NewsBySourceActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.errorTextView)
    TextView mErrorTextView;



    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private NewsListAdapter adapter;
    private String mSearchQuery;
    public static final String TAG = NewsPageActivity.class.getSimpleName();

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_page);

        ButterKnife.bind(this);

        fetchNewsBySource("cnn");


    }
    public void fetchNewsBySource(String mSearchQuery) {
        Intent intent = getIntent();
        mSearchQuery = intent.getStringExtra("source");

        NewsApi client = NewsClient.getNewsClient();

        Call<CountryNewsResponse> call = client.getNewsBySource(mSearchQuery, NEWS_API_KEY);

        call.enqueue(new Callback<CountryNewsResponse>() {
            @Override
            public void onResponse(Call<CountryNewsResponse> call, Response<CountryNewsResponse> response) {

                hideProgressBar();
                if (response.isSuccessful()) {
                    Log.i("START IF", "BEGINNING");
                    articles = response.body().getArticles();
                    adapter = new NewsListAdapter(articles, NewsBySourceActivity.this);
                    mRecyclerView.setAdapter(adapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NewsBySourceActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);
                    Log.i("END IF", "LAST IF ");

                } else {
                    showFailureMessage();
                    hideProgressBar();
                    Log.i("ELSE", "else tag");
                }
            }

            @Override
            public void onFailure(Call<CountryNewsResponse> call, Throwable t) {
                showFailureMessage();
                hideProgressBar();
                Log.i("FAILURE", "on failure");
            }
        });
    }


    private void showFailureMessage() {
        mErrorTextView.setText("Bruh, some bug is bothering me! Let's try searching again.");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showNotFoundMessage(){
        mErrorTextView.setText("Sorry, we didn't find anything on that for now!");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuInflater inflater1 = getMenuInflater();
        inflater1.inflate(R.menu.toobar_search, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search here");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(NewsBySourceActivity.this, NewsBySourceActivity.class);
                intent.putExtra("source", query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_logout){
            logout();
            return true;
        } if (id == R.id.likedNews){
            Intent intent = new Intent(NewsBySourceActivity.this, LikedNewsActivity.class);
            startActivity(intent);
            return false;
        }
        if (id == R.id.sourceNews){
            Toast.makeText(this, "Already Opened", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(NewsBySourceActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}