package com.abel.whatsup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SourceNewsActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.sourceNews)
    Button mNewButton;
    @BindView(R.id.sourceInput)
    EditText mSourceInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_news);
        ButterKnife.bind(this);

        mNewButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == mNewButton ){
            String input = mSourceInput.getText().toString();
            Intent intent = new Intent(SourceNewsActivity.this, NewsPageActivity.class);
            intent.putExtra("country", input);
            startActivity(intent);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuInflater inflater1 = getMenuInflater();
        inflater1.inflate(R.menu.toobar_search, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Filter here");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(SourceNewsActivity.this, NewsBySourceActivity.class);
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
            Intent intent = new Intent(SourceNewsActivity.this, LikedNewsActivity.class);
            startActivity(intent);
            return false;
        } if (id == R.id.sourceNews){
            Intent intent = new Intent(SourceNewsActivity.this, SourceNewsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(SourceNewsActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


}
