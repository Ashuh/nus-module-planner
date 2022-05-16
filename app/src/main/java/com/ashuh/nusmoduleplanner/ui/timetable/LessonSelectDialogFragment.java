package com.ashuh.nusmoduleplanner.ui.timetable;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.SemesterType;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.Lesson;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.LessonType;
import com.ashuh.nusmoduleplanner.data.model.timetable.AssignedModule;
import com.ashuh.nusmoduleplanner.data.model.timetable.TimetableEvent;

import org.threeten.bp.format.TextStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LessonSelectDialogFragment extends DialogFragment {

    private final SemesterType semType;
    private final String moduleCode;
    private final LessonType lessonType;
    private final List<String> altLessonCodes;
    private final TimetableViewModel viewModel;

    public LessonSelectDialogFragment(TimetableEvent event, TimetableViewModel viewModel) {
        semType = event.getSemType();
        moduleCode = event.getAssignedModule().getModuleCode();
        lessonType = event.getLesson().getType();
        altLessonCodes = event.getAlternateLessonCodes();
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Select Lesson");

        builder.setItems(getDialogItems(), (dialogInterface, i) -> {
            String selectedLessonCode = altLessonCodes.get(i);
            viewModel.updateAssignedLesson(moduleCode, semType,
                    lessonType, selectedLessonCode);
        });

        return builder.create();
    }

    private CharSequence[] getDialogItems() {
        List<String> dialogItems = new ArrayList<>();
        AssignedModule assignedModule = viewModel.getAssignedModule(moduleCode, semType);

        for (String code : altLessonCodes) {
            List<Lesson> lessons = assignedModule.getTimetable(lessonType, code);
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
            sb.append(lesson.getScheduleDescription());
            sb.append('\n');
        }

        return sb.toString();
    }
}
