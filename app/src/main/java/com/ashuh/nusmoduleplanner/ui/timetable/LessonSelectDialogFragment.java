package com.ashuh.nusmoduleplanner.ui.timetable;

import android.app.Dialog;
import android.content.DialogInterface;
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

public class LessonSelectDialogFragment extends DialogFragment implements
        DialogInterface.OnClickListener {

    private static final String TEXT_DIALOG_TITLE = "Select Lesson";
    private static final String TEXT_CLASS_INFO_FORMAT = "[%s] %s\n";
    private static final String TEXT_LESSON_INFO_FORMAT = "%s %s - %s %s\n";

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
        builder.setTitle(TEXT_DIALOG_TITLE)
                .setItems(getDialogItems(), this);
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
        String classInfoText = String.format(TEXT_CLASS_INFO_FORMAT,
                lessonType.getShortName(),
                lessons.get(0).getClassNo());
        StringBuilder builder = new StringBuilder(classInfoText);

        for (Lesson lesson : lessons) {
            String lessonInfoText = String.format(TEXT_LESSON_INFO_FORMAT,
                    lesson.getDay().getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                    lesson.getStartTime(),
                    lesson.getEndTime(),
                    lesson.getWeeksDescription());

            builder.append(lessonInfoText);
        }

        return builder.toString();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        String selectedLessonCode = altLessonCodes.get(i);
        viewModel.updateAssignedLesson(moduleCode, semType, lessonType, selectedLessonCode);
    }
}
