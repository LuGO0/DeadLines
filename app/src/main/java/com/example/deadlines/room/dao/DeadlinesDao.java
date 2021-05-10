package com.example.deadlines.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.deadlines.room.models.ProjectDeadline;

import java.util.List;

@Dao
public interface DeadlinesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ProjectDeadline deadline);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllDeadlines(List<ProjectDeadline> deadlines);

    @Delete
    void deleteDeadline(ProjectDeadline deadline);

    @Query("DELETE FROM deadlines_table")
    void deleteAll();

    @Query("SELECT * from deadlines_table ORDER BY deadlineDate ASC")
    LiveData<List<ProjectDeadline>> getAllDeadlines();

    @Query("SELECT * from deadlines_table where date(:deadLineDate) between date(:deadLineDate) and date(:checkDate)")
    LiveData<List<ProjectDeadline>> getCurrentDeadlines(String deadLineDate, String checkDate);

    @Query("SELECT * from deadlines_table where date(:deadLineDate) > date(:checkDate)")
    LiveData<List<ProjectDeadline>> getUpcomingDeadlines(String deadLineDate, String checkDate);
}