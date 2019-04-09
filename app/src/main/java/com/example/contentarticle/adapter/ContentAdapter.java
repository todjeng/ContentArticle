package com.example.contentarticle.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contentarticle.R;
import com.example.contentarticle.activity.DetailContentActivity;
import com.example.contentarticle.activity.HomeActivity;
import com.example.contentarticle.activity.UpdateActivity;
import com.example.contentarticle.helper.DatabaseClient;
import com.example.contentarticle.model.room.Content;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {

    private Context mContext;
    private List<Content> contentList;

    public ContentAdapter(Context mContext, List<Content> contentList){
        this.mContext = mContext;
        this.contentList = contentList;
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_content, viewGroup, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContentViewHolder viewHolder, int position) {
        Content content = contentList.get(position);
        viewHolder.mJudul.setText(content.getJudul());
        viewHolder.mTanggal.setText(content.getTanggal());
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mJudul, mTanggal;
        Button mBtnEdit, mBtnDelete;

        public ContentViewHolder(View itemView) {
            super(itemView);

            mJudul = itemView.findViewById(R.id.judul);
            mTanggal = itemView.findViewById(R.id.tanggal);
            mBtnEdit = itemView.findViewById(R.id.btnEdit);
            mBtnDelete = itemView.findViewById(R.id.btnDelete);

            itemView.setOnClickListener(this);

            mBtnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Are u Sure?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            delete(contentList.get(getAdapterPosition()));
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            mBtnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Content content = contentList.get(getAdapterPosition());
                    Intent intent = new Intent(mContext, UpdateActivity.class);
                    intent.putExtra("editContent", content);
                    mContext.startActivity(intent);
                    ((Activity) mContext).finish();
                }
            });
        }

        @Override
        public void onClick(View view) {
            Content content = contentList.get(getAdapterPosition());

            Intent intent = new Intent(mContext, DetailContentActivity.class);
            intent.putExtra("detailContent", content);
            mContext.startActivity(intent);
            ((Activity) mContext).finish();
        }
    }

    private void delete(final Content content){

            class DeleteContent extends AsyncTask<Void,Void,Void>{

                @Override
                protected Void doInBackground(Void... voids) {

                    DatabaseClient.getInstance(mContext).getAppDatabase()
                            .contentDao().delete(content);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Toast.makeText(mContext, "Data Berhasil Di Hapus", Toast.LENGTH_SHORT).show();
                    mContext.startActivity(new Intent(mContext, HomeActivity.class));
                    ((Activity) mContext).finish();
                }
            }

            DeleteContent dc = new DeleteContent();
            dc.execute();
    }

}
