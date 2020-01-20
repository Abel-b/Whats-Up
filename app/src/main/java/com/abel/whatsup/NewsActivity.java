package com.abel.whatsup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsActivity extends AppCompatActivity{


    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    private String mUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ButterKnife.bind(this);

        openUrl();
    }

    public void openUrl(){
        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");

        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(mUrl);
        hideProgressBar();
    }
    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }


}
