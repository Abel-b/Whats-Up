package com.abel.whatsup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
}
