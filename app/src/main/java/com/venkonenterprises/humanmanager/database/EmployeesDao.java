package com.venkonenterprises.humanmanager.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EmployeesDao {

    @Query("SELECT * FROM DatabaseEmployee")
    LiveData<List<DatabaseEmployee>> getEmployees();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEmployee(DatabaseEmployee employee);

    @Delete
    void deleteEmployee(DatabaseEmployee employee);

    @Query("DELETE FROM DatabaseEmployee")
    void deleteAllEmployees();
}