package com.ashuh.nusmoduleplanner.timetable.presentation;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.Lesson;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonOccurrence;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;

import java.time.format.TextStyle;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class LessonSelectDialogFragment extends DialogFragment implements
        DialogInterface.OnClickListener {
    private static final String TEXT_DIALOG_TITLE = "Select Lesson";
    private static final String TEXT_CLASS_INFO_FORMAT = "[%s] %s\n";
    private static final String TEXT_LESSON_INFO_FORMAT = "%s %s - %s %s\n";

    private final String moduleCode;
    private final Semester semester;
    private final LessonType lessonType;
    private final List<Lesson> altLessonOptions;
    private final TimetableViewModel viewModel;

    public LessonSelectDialogFragment(String moduleCode, Semester semester,
                                      LessonType lessonType, List<Lesson> altLessonOptions,
                                      TimetableViewModel viewModel) {
        this.moduleCode = moduleCode;
        this.semester = semester;
        this.lessonType = lessonType;
        Collections.sort(altLessonOptions);
        this.altLessonOptions = Collections.unmodifiableList(altLessonOptions);
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
        return altLessonOptions.stream()
                .map(this::getDialogString)
                .toArray(CharSequence[]::new);
    }

    private String getDialogString(Lesson lesson) {
        String classInfoText = String.format(TEXT_CLASS_INFO_FORMAT,
                lesson.getLessonType().getShortName(),
                lesson.getLessonNo());
        StringBuilder builder = new StringBuilder(classInfoText);

        String weeksDescription = "temp"; // TODO: implement

        for (LessonOccurrence occurrence : lesson.getOccurrences()) {
            String lessonInfoText = String.format(TEXT_LESSON_INFO_FORMAT,
                    occurrence.getDay().getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                    occurrence.getStartTime(),
                    occurrence.getEndTime(),
                    weeksDescription);

            builder.append(lessonInfoText);
        }

        return builder.toString();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        String selectedLessonNo = altLessonOptions.get(i).getLessonNo();
        viewModel.updateAssignedLesson(moduleCode, semester, lessonType, selectedLessonNo);
    }
}
