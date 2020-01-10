package com.abel.whatsup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.countrySearch)
    EditText mCountrySearch;
    @BindView(R.id.searchButton) Button mSearchButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ButterKnife.bind(this);

        mSearchButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    getSupportActionBar().setTitle("What's Up, " + user.getDisplayName() + "!");
                }else {

                }
            }
        };

    }

    @Override
    public void onClick(View v) {
        if (v == mSearchButton){
            String Country = mCountrySearch.getText().toString();
            Intent intent = new Intent(NewsActivity.this, NewsPageActivity.class);
            intent.putExtra("country", Country);
            startActivity(intent);

        }

    }
}
