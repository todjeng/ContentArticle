package com.example.contentarticle.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.contentarticle.R;

public class DetailContentActivity extends AppCompatActivity {

    private TextView mUsername, mContent, mGender, mDate, mPhone;
    private ImageButton mBtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_content);

        mUsername = (TextView) findViewById(R.id.username);
        mContent = (TextView) findViewById(R.id.content);
        mGender = (TextView) findViewById(R.id.gender);
        mDate = (TextView) findViewById(R.id.date);
        mPhone = (TextView) findViewById(R.id.phone);
        mBtnBack = (ImageButton) findViewById(R.id.btnBack);

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailContentActivity.this, HomeActivity.class));
                finish();
            }
        });
    }
}
