package com.ashuh.nusmoduleplanner.data.source.timetable;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.SemesterType;
import com.ashuh.nusmoduleplanner.data.model.timetable.AssignedModule;

import java.util.List;

@Dao
public interface TimetableDAO {

    @Query("SELECT * FROM assignedmodule")
    LiveData<List<AssignedModule>> getAll();

    @Query("SELECT * FROM assignedmodule WHERE semType = :semType")
    LiveData<List<AssignedModule>> getAssignedModules(SemesterType semType);

    @Query("SELECT * FROM assignedmodule WHERE moduleCode = :moduleCode AND semType = :semType")
    AssignedModule getAssignedModule(String moduleCode, SemesterType semType);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<AssignedModule> entries);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AssignedModule entry);

    @Query("DELETE FROM assignedmodule WHERE (semType = :semType) AND (moduleCode = :moduleCode)")
    void delete(SemesterType semType, String moduleCode);

    @Query("DELETE FROM assignedmodule WHERE (moduleCode = :moduleCode)")
    void delete(String moduleCode);

    @Query("DELETE FROM assignedmodule")
    void deleteAll();

    @Update
    void update(AssignedModule assignedModule);
}
