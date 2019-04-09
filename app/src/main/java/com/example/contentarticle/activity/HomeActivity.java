package com.example.contentarticle.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.contentarticle.R;
import com.example.contentarticle.adapter.ContentAdapter;
import com.example.contentarticle.helper.DatabaseClient;
import com.example.contentarticle.model.room.Content;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_tasks);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startActivity(new Intent(HomeActivity.this,AddActivity.class));
                //finish();
            }
        });

        //Get All Data
        getContent();
    }

    public void getContent(){
        class GetAllContent extends AsyncTask<Void, Void, List<Content>> {

            @Override
            protected List<Content> doInBackground(Void... voids) {
                List<Content> listContent;
                listContent = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .contentDao().getAll();
                return listContent;
            }

            @Override
            protected void onPostExecute(List<Content> contentList) {
                super.onPostExecute(contentList);

                ContentAdapter contentAdapter = new ContentAdapter(HomeActivity.this,contentList);
                mRecyclerView.setAdapter(contentAdapter);
            }
        }

        GetAllContent allContent = new GetAllContent();
        allContent.execute();

    }



}
