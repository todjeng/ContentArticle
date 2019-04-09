package com.example.contentarticle.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.contentarticle.R;
import com.example.contentarticle.model.room.Content;

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

        /**
         * Mengambil data content dari passing data (putExtra)
         */
        final Content content = (Content) getIntent().getSerializableExtra("detailContent");
        contentDetail(content);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && checkSelfPermission(Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.CALL_PHONE},
                        110);
            }
            return;
        }
    }

    private void contentDetail(final Content content){
        mUsername.setText(content.getJudul());
        mContent.setText(content.getMyContent());
        mGender.setText(content.getCategory());
        mDate.setText(content.getTanggal());
        mPhone.setText(content.getPhone());

        mPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + content.getPhone()));
                startActivity(callIntent);
            }
        });
    }
}
