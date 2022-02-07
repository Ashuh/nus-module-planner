package com.ashuh.nusmoduleplanner.ui.timetable;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TimetableCollectionAdapter extends FragmentStateAdapter {
    public TimetableCollectionAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new TimetableTabFragment();
        Bundle args = new Bundle();
        args.putInt(TimetableTabFragment.ARG_SEMESTER, position + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}