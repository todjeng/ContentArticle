package com.example.contentarticle.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.contentarticle.R;

public class AddActivity extends AppCompatActivity {

    private Toolbar mtToolbar;
    private EditText judul, content, tanggal, phone;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mtToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtToolbar);

        judul = (EditText) findViewById(R.id.judul);
        tanggal = (EditText) findViewById(R.id.tanggal);
        phone = (EditText) findViewById(R.id.phone);

        save = (Button) findViewById(R.id.btnSave);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddActivity.this, "Oke", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
