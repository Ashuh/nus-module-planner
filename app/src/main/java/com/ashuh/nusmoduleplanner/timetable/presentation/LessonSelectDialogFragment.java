package com.ashuh.nusmoduleplanner.timetable.presentation;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.ashuh.nusmoduleplanner.timetable.presentation.model.UiLesson;
import com.ashuh.nusmoduleplanner.timetable.presentation.model.UiLessonOccurrence;

import java.util.List;

public class LessonSelectDialogFragment extends DialogFragment implements
        DialogInterface.OnClickListener {
    private static final String TEXT_DIALOG_TITLE = "Select Lesson";
    private static final String TEXT_CLASS_INFO_FORMAT = "[%s] %s\n";
    private static final String TEXT_LESSON_INFO_FORMAT = "%s %s - %s %s\n";

    private final List<UiLesson> alternateLessons;

    public LessonSelectDialogFragment(List<UiLesson> alternateLessons) {
        this.alternateLessons = alternateLessons;
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
        return alternateLessons.stream()
                .map(this::getDialogString)
                .toArray(CharSequence[]::new);
    }

    private String getDialogString(UiLesson lesson) {
        String classInfoText = String.format(TEXT_CLASS_INFO_FORMAT,
                lesson.getLessonType(), lesson.getLessonNo());
        StringBuilder builder = new StringBuilder(classInfoText);

        for (UiLessonOccurrence occurrence : lesson.getOccurrences()) {
            String lessonInfoText = String.format(TEXT_LESSON_INFO_FORMAT,
                    occurrence.getDay(),
                    occurrence.getStartTime(),
                    occurrence.getEndTime(),
                    occurrence.getWeeks());

            builder.append(lessonInfoText);
        }

        return builder.toString();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        alternateLessons.get(i).onClick();
    }
}
