package com.ashuh.nusmoduleplanner.common.data.local.dao;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.ashuh.nusmoduleplanner.common.data.local.model.module.ExamEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.ModuleAggregate;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.ModuleEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.ModuleFulfillsCrossRefEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.WorkloadEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.lesson.LessonAggregate;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.lesson.LessonEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.lessonoccurrence.LessonOccurrenceEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.prerequisite.PrerequisiteTreeAggregate;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.prerequisite.PrerequisiteTreeEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.prerequisite.PrerequisiteTreeNodeEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.reading.LessonNoMappingEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.reading.ModuleReadingAggregate;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.reading.ModuleReadingEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.semesterdatum.SemesterDatumAggregate;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.semesterdatum.SemesterDatumEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.weeks.WeekNumberEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.weeks.WeeksAggregate;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.weeks.WeeksEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.weeks.WeeksWeekNumberCrossRefEntity;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Dao
public abstract class ModuleDao {
    @Transaction
    @Query("SELECT * FROM ModuleEntity WHERE moduleCode = :moduleCode")
    public abstract ModuleAggregate getModule(String moduleCode);

    @Transaction
    @Query("SELECT * FROM ModuleReadingEntity WHERE semester = :semester")
    public abstract LiveData<List<ModuleReadingAggregate>> getModuleReadings(Semester semester);

    @Query("UPDATE LessonNoMappingEntity "
            + "SET lessonNo = :lessonNo "
            + "WHERE LessonNoMappingEntity.lessonType = :lessonType "
            + "AND EXISTS (SELECT 1 FROM ModuleReadingEntity "
            + "WHERE LessonNoMappingEntity.ownerId = ModuleReadingEntity.id "
            + "AND ModuleReadingEntity.moduleCode = :moduleCode "
            + "AND ModuleReadingEntity.semester = :semester)")
    public abstract void updateLessonNoMapping(String moduleCode, Semester semester,
                                               LessonType lessonType, String lessonNo);

    @Query("DELETE FROM ModuleReadingEntity "
            + "WHERE moduleCode = :moduleCode "
            + "AND semester = :semester")
    public abstract void deleteModuleReading(String moduleCode, Semester semester);

    @Transaction
    public void insertModuleReading(ModuleReadingAggregate moduleReadingAggregate) {
        insertModule(moduleReadingAggregate.getModuleAggregate());
        long id = insertModuleReading(moduleReadingAggregate.getTimetableEntry());

        moduleReadingAggregate.getLessonNoMappings().forEach(mapping -> {
            mapping.setOwnerId(id);
            insertLessonNoMapping(mapping);
        });
    }

    @Transaction
    public void insertModule(ModuleAggregate moduleAggregate) {
        upsertModule(moduleAggregate.getModule());
        String moduleCode = moduleAggregate.getModule().getModuleCode();

        moduleAggregate.getSemesterDatumAggregates()
                .forEach(datum -> {
                    datum.getSemesterDatum().setOwnerModuleCode(moduleCode);
                    insertSemesterDatum(datum);
                });

        moduleAggregate.getPrerequisiteTreeAggregate()
                .ifPresent(tree -> {
                    tree.getTree().setOwnerModuleCode(moduleCode);
                    insertPrerequisiteTree(tree);
                });

        moduleAggregate.getWorkload()
                .ifPresent(workload -> {
                    workload.setOwnerModuleCode(moduleCode);
                    insertWorkload(workload);
                });

        moduleAggregate.getFulfillRequirements().stream()
                .map(fulfilledModuleCode
                        -> new ModuleFulfillsCrossRefEntity(fulfilledModuleCode, moduleCode))
                .forEach(this::insertModuleFulfillsCrossRef);
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract long insertModuleReading(ModuleReadingEntity moduleReading);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void insertLessonNoMapping(LessonNoMappingEntity lessonNoMapping);

    @Transaction
    protected void upsertModule(ModuleEntity module) {
        long id = insertModule(module);
        if (id == -1) {
            updateModule(module);
        }
    }

    @Transaction
    protected void insertSemesterDatum(SemesterDatumAggregate semesterDatumAggregate) {
        long id = insertSemesterDatum(semesterDatumAggregate.getSemesterDatum());

        semesterDatumAggregate.getExam().ifPresent(exam -> {
            exam.setOwnerId(id);
            insertExam(exam);
        });

        semesterDatumAggregate.getLessonAggregates().forEach(lesson -> {
            lesson.getLesson().setOwnerId(id);
            insertLesson(lesson);
        });
    }

    @Transaction
    protected void insertPrerequisiteTree(PrerequisiteTreeAggregate prerequisiteTree) {
        long treeId = insertPrerequisiteTree(prerequisiteTree.getTree());
        Map<Integer, Long> indexToId = new HashMap<>();

        for (PrerequisiteTreeNodeEntity node : prerequisiteTree.getNodes()) {
            Long nodeParentId = Optional.ofNullable(node.getParentTreeIndex())
                    .map(indexToId::get)
                    .orElse(null);
            node.setParentNodeId(nodeParentId);
            node.setOwnerId(treeId);
            long nodeId = insertPrerequisiteTreeNode(node);
            indexToId.put(node.getTreeIndex(), nodeId);
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void insertWorkload(WorkloadEntity workload);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract void insertModuleFulfillsCrossRef(ModuleFulfillsCrossRefEntity crossRef);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract long insertModule(ModuleEntity module);

    @Update
    protected abstract void updateModule(ModuleEntity module);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract long insertSemesterDatum(SemesterDatumEntity semesterDatum);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void insertExam(ExamEntity exam);

    @Transaction
    protected void insertLesson(LessonAggregate lessonAggregate) {
        long lessonId = insertLesson(lessonAggregate.getLesson());
        lessonAggregate.getLessonOccurrenceAggregates().forEach(occurrenceAggregate -> {
            LessonOccurrenceEntity occurrence = occurrenceAggregate.getLessonOccurrence();
            occurrence.setOwnerId(lessonId);
            long occurrenceId = insertLessonOccurrence(occurrence);
            WeeksAggregate weeksAggregate = occurrenceAggregate.getWeeksAggregate();
            weeksAggregate.getWeeksEntity().setOwnerId(occurrenceId);
            insertWeeks(weeksAggregate);
        });
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract long insertPrerequisiteTree(PrerequisiteTreeEntity prerequisiteTree);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract long insertPrerequisiteTreeNode(PrerequisiteTreeNodeEntity node);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract long insertLesson(LessonEntity lesson);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract long insertLessonOccurrence(LessonOccurrenceEntity occurrence);

    @Transaction
    protected void insertWeeks(WeeksAggregate weeksAggregate) {
        WeeksEntity weeksEntity = weeksAggregate.getWeeksEntity();
        long id = insertWeeks(weeksEntity);

        weeksAggregate.getWeekNumbers().forEach(weekNumber -> {
            insertWeekNumber(weekNumber);
            WeeksWeekNumberCrossRefEntity crossRef
                    = new WeeksWeekNumberCrossRefEntity(id, weekNumber.getWeekNumber());
            insertWeeksWeekNumberCrossRef(crossRef);
        });
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract long insertWeeks(WeeksEntity weeks);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract void insertWeekNumber(WeekNumberEntity weekNumber);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract void insertWeeksWeekNumberCrossRef(WeeksWeekNumberCrossRefEntity crossRef);
}
