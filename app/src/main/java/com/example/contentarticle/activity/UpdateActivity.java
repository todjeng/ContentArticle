package com.example.contentarticle.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.contentarticle.R;
import com.example.contentarticle.model.room.Content;

public class UpdateActivity extends AppCompatActivity {

    EditText mJudul, mContent, mTanggal, mPhone;
    RadioGroup mRadio_status;
    RadioButton mRadio_male, mRadio_female;
    ImageButton mBtnBack;
    Button mBtnSave;

    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mJudul = (EditText) findViewById(R.id.judulUpdt);
        mContent = (EditText) findViewById(R.id.contentUpdt);
        mTanggal = (EditText) findViewById(R.id.tanggalUpdt);
        mPhone = (EditText) findViewById(R.id.phoneUpdt);
        mRadio_status = (RadioGroup) findViewById(R.id.radio_statusUpdt);
        mRadio_male = (RadioButton) findViewById(R.id.radio_maleUpdt);
        mRadio_female = (RadioButton) findViewById(R.id.radio_femaleUpdt);
        mBtnBack = (ImageButton) findViewById(R.id.btnBackUpdt);
        mBtnSave = (Button) findViewById(R.id.btnSaveUpdt);
        status = "";

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateActivity.this, HomeActivity.class));
                finish();
            }
        });

        final Content content = (Content) getIntent().getSerializableExtra("editContent");
        editContent(content);
    }

    private void editContent(final Content content){
        mJudul.setText(content.getJudul());
        mContent.setText(content.getMyContent());
        mTanggal.setText(content.getTanggal());
        mPhone.setText(content.getPhone());
        status = content.getCategory();
        switch (status){
            case (String) "Male":
                mRadio_male.setChecked(true);
                break;
            case (String) "Female":
                mRadio_female.setChecked(true);
                break;
        }

    }
}
