package com.ashuh.nusmoduleplanner.ui.timetable;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TimetablePagerAdapter extends FragmentStateAdapter {

    private static final int NUM_TABS = 4;

    public TimetablePagerAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new TimetablePageFragment();
        Bundle args = new Bundle();
        args.putInt(TimetablePageFragment.ARG_SEMESTER, position + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return NUM_TABS;
    }
}
