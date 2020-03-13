package com.example.deadlines.Repositories;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.deadlines.models.ProjectDeadline;

import java.util.List;

public interface DeadlinesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ProjectDeadline deadline);

    @Delete
    void deleteDeadline(ProjectDeadline deadline);

    @Query("DELETE FROM deadlines_table")
    void deleteAll();

    @Query("SELECT * from deadlines_table ORDER BY deadlineDate ASC")
    LiveData<List<ProjectDeadline>> getAllDeadlines();

    //getting a single deadline we might not use it btw well see later!!
    @Query("SELECT * from deadlines_table LIMIT 1")
    ProjectDeadline[] getAnyDeadline();
}