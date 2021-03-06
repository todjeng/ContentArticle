package com.example.contentarticle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.contentarticle.model.room.Content;

import java.util.List;

@Dao
public interface ContentDao {

    @Insert
    void insert(Content content);

    @Query("SELECT * FROM Content")
    List<Content> getAll();

    @Delete
    void delete(Content content);

    @Update
    void update(Content content);


}
