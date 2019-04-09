package com.example.contentarticle.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.contentarticle.R;
import com.example.contentarticle.helper.DatabaseClient;
import com.example.contentarticle.model.room.Content;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    private EditText judul, content, tanggal, phone;
    private Button save;
    private ImageButton btnBack;
    private RadioGroup radio_status;
    private RadioButton radio_male, radio_female;

    /*
    *Inisiasi kalender
     */
    final Calendar myCalendar = Calendar.getInstance();

    /*
    *inisiasi listener ketika klik ok
     */
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    /**
     * Method untuk update format dari DatePickerDialog ke EditText
     */
    private void updateLabel(){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tanggal.setText(sdf.format(myCalendar.getTime()));
    }

    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);


        judul = (EditText) findViewById(R.id.judul);
        content = (EditText) findViewById(R.id.content);
        tanggal = (EditText) findViewById(R.id.tanggal);
        phone = (EditText) findViewById(R.id.phone);

        save = (Button) findViewById(R.id.btnSave);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        radio_status = (RadioGroup) findViewById(R.id.radio_status);
        radio_male = (RadioButton) findViewById(R.id.radio_male);
        radio_female = (RadioButton) findViewById(R.id.radio_female);

        status = "";
        radio_status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_male:
                        status = "Male";
                        break;
                    case R.id.radio_female:
                        status = "Female";
                        break;
                }
            }
        });


        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddActivity.this, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(judul.getText().toString().equals("") ||
                    content.getText().toString().equals("") ||
                    status.equals("") ||
                    phone.getText().equals("") ||
                    tanggal.getText().equals("")){
                        Toast.makeText(AddActivity.this, "Data Masih Ada Yang Kosong", Toast.LENGTH_SHORT).show();
                }else{
                    save();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddActivity.this,HomeActivity.class));
                finish();
            }
        });
    }

    public void save(){
        final String mJudul = judul.getText().toString();
        final String mContent = content.getText().toString();
        final String mGender = status;
        final String mPhone = phone.getText().toString();
        final String mTanggal = tanggal.getText().toString();

            class SaveContent extends AsyncTask<Void,Void,Void>{

                @Override
                protected Void doInBackground(Void... voids) {
                    Content content = new Content();
                    content.setJudul(mJudul);
                    content.setMyContent(mContent);
                    content.setCategory(mGender);
                    content.setPhone(mPhone);
                    content.setTanggal(mTanggal);

                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .contentDao().insert(content);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Toast.makeText(AddActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddActivity.this, HomeActivity.class));
                    finish();
                }
            }

            SaveContent saveContent = new SaveContent();
            saveContent.execute();
    }
}
