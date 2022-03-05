package com.ashuh.nusmoduleplanner.data.source.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ashuh.nusmoduleplanner.data.model.module.Semester;
import com.ashuh.nusmoduleplanner.data.model.timetable.AssignedModule;

import java.util.List;

@Dao
public interface TimetableDAO {
    @Query("SELECT * FROM assigned_modules")
    LiveData<List<AssignedModule>> getAll();

    @Query("SELECT * FROM assigned_modules WHERE semType = :semType")
    LiveData<List<AssignedModule>> getAssignedModules(Semester semType);

    @Query("SELECT * FROM assigned_modules WHERE moduleCode = :moduleCode AND semType = :semType")
    AssignedModule getAssignedModule(String moduleCode, Semester semType);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<AssignedModule> entries);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AssignedModule entry);

    @Query("DELETE FROM assigned_modules WHERE (semType = :semType) AND (moduleCode = " +
            ":moduleCode)")
    void delete(Semester semType, String moduleCode);

    @Query("DELETE FROM assigned_modules WHERE (moduleCode = :moduleCode)")
    void delete(String moduleCode);

    @Query("DELETE FROM assigned_modules")
    void deleteAll();

    @Update
    void update(AssignedModule assignedModule);
}