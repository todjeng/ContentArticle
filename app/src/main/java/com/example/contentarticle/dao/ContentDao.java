package com.example.contentarticle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.contentarticle.model.room.Content;

import java.util.List;

@Dao
public interface ContentDao {

    @Insert
    void insert(Content content);

    @Query("SELECT * FROM Content")
    List<Content> getAll();
}
