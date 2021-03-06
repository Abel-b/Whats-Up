package com.abel.whatsup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.etText) EditText mEtText;

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int Counter = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Name = (EditText) findViewById(R.id.etText);
        Password = (EditText) findViewById(R.id.etPassword);
        Info = (TextView) findViewById(R.id.tvInfo);
        Login = (Button) findViewById(R.id.btnLogin);

        Info.setText("No of attempts remaining: 5");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });

    }

    private void validate(String userName, String userPassword){
        if (userName.equals("Admin") && userPassword.equals("1234") ){
            String user = mEtText.getText().toString();
            Intent intent = new Intent(MainActivity.this, NewsPageActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            Toast.makeText(MainActivity.this, "Successfully Logged in", Toast.LENGTH_LONG).show();
        }else {
            Counter --;

            Info.setText("No of attempts remaining: " + String.valueOf(Counter));

            if(Counter == 0){
                Login.setEnabled(false);
            }

        }
    }

}
