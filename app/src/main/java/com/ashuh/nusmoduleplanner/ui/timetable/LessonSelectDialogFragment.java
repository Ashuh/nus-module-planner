package com.ashuh.nusmoduleplanner.ui.timetable;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.ashuh.nusmoduleplanner.data.module.Lesson;
import com.ashuh.nusmoduleplanner.data.module.SemesterDetail;
import com.ashuh.nusmoduleplanner.data.module.SemesterType;
import com.ashuh.nusmoduleplanner.timetable.TimetableDataSource;
import com.ashuh.nusmoduleplanner.timetable.TimetableEvent;

import org.threeten.bp.format.TextStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LessonSelectDialogFragment extends DialogFragment {
    private final SemesterType semType;
    private final String moduleCode;
    private final Lesson.Type lessonType;
    private final List<String> altLessonCodes;

    public LessonSelectDialogFragment(TimetableEvent event) {
        semType = event.getSemType();
        moduleCode = event.getAssignedModule().getModuleDetail().getModuleCode();
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

        for (String code : altLessonCodes) {
            List<Lesson> lessons = semData.getLessons(lessonType, code);
            dialogItems.add(getDialogString(lessons));
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
