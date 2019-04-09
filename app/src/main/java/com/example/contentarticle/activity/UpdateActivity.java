package com.example.contentarticle.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class UpdateActivity extends AppCompatActivity {

    EditText mJudul, mContent, mTanggal, mPhone;
    RadioGroup mRadio_status;
    RadioButton mRadio_male, mRadio_female;
    ImageButton mBtnBack;
    Button mBtnSave;

    String status;

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
        mTanggal.setText(sdf.format(myCalendar.getTime()));
    }


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


        /**
         * event ketika kolom tanggal di klik
         */
        mTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(UpdateActivity.this, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateActivity.this, HomeActivity.class));
                finish();
            }
        });

        final Content content = (Content) getIntent().getSerializableExtra("editContent");
        editContent(content);

        /**
         * Untuk event radio button
         */
        mRadio_status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_maleUpdt:
                        status = "Male";
                        break;
                    case R.id.radio_femaleUpdt:
                        status = "Female";
                        break;
                }
            }
        });

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedProses(content);
            }
        });
    }


    /**
     * Tampil Content Yang Akan Di Edit
     * @param content
     */
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

    private void updatedProses(final Content content){
        final String judul = mJudul.getText().toString();
        final String txtContent = mContent.getText().toString();
        final String tanggal = mTanggal.getText().toString();
        final String phone = mPhone.getText().toString();
        final String category = status;

            class Updated extends AsyncTask<Void,Void,Void>{

                @Override
                protected Void doInBackground(Void... voids) {
                    content.setJudul(judul);
                    content.setMyContent(txtContent);
                    content.setTanggal(tanggal);
                    content.setPhone(phone);
                    content.setCategory(category);

                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .contentDao().update(content);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Toast.makeText(UpdateActivity.this, "Data Berhasi Di Updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateActivity.this, HomeActivity.class));
                    finish();
                }
            }

            Updated updated = new Updated();
            updated.execute();
     }
}
