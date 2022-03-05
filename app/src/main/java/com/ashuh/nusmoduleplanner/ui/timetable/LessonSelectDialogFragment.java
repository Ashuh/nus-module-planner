package com.ashuh.nusmoduleplanner.ui.timetable;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.ashuh.nusmoduleplanner.data.model.nusmods.Lesson;
import com.ashuh.nusmoduleplanner.data.model.nusmods.Semester;
import com.ashuh.nusmoduleplanner.data.model.nusmods.SemesterDetail;
import com.ashuh.nusmoduleplanner.data.model.nusmods.Timetable;
import com.ashuh.nusmoduleplanner.data.model.timetable.TimetableEvent;
import com.ashuh.nusmoduleplanner.data.source.TimetableDataSource;

import org.threeten.bp.format.TextStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LessonSelectDialogFragment extends DialogFragment {
    private final Semester semType;
    private final String moduleCode;
    private final Lesson.Type lessonType;
    private final List<String> altLessonCodes;

    public LessonSelectDialogFragment(TimetableEvent event) {
        semType = event.getSemType();
        moduleCode = event.getModuleCode();
        lessonType = event.getLesson().getType();
        altLessonCodes = event.getAlternateLessonCodes();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Select Lesson");

        builder.setItems(getDialogItems(), (dialogInterface, i) -> {
            String selectedLessonCode = altLessonCodes.get(i);
            TimetableDataSource.getInstance().updateAssignedLesson(moduleCode, semType,
                    lessonType, selectedLessonCode);
        });

        return builder.create();
    }

    private CharSequence[] getDialogItems() {
        List<String> dialogItems = new ArrayList<>();
        SemesterDetail semData =
                TimetableDataSource.getInstance().getAssignedModule(moduleCode, semType)
                        .getModuleDetail()
                        .getSemesterDetail(semType);

        Timetable timetable = new Timetable(semData.getLessons(), moduleCode, semType);
        for (String code : altLessonCodes) {
            dialogItems.add(getDialogString(timetable.getLessons(lessonType, code)));
        }

        return dialogItems.toArray(new CharSequence[0]);
    }

    private String getDialogString(List<Lesson> lessons) {
        StringBuilder sb =
                new StringBuilder().append('[').append(lessonType.getShortName()).append("] ")
                        .append(lessons.get(0).getClassNo()).append('\n');

        for (Lesson lesson : lessons) {
            String dayTime =
                    lesson.getDay().getDisplayName(TextStyle.SHORT, Locale.getDefault()) + " "
                            + lesson.getStartTime() + "-" + lesson.getEndTime();
            sb.append(dayTime).append(' ');

            if (!lesson.isOccurringEveryWeek()) {
                sb.append("Weeks: ").append(lesson.getWeeksString());
            }

            sb.append('\n');
        }

        return sb.toString();
    }
}
