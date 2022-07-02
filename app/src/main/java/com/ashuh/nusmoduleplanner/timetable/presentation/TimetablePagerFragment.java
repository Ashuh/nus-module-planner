package com.ashuh.nusmoduleplanner.timetable.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.ashuh.nusmoduleplanner.R;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy;

public class TimetablePagerFragment extends Fragment implements TabConfigurationStrategy {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timetable, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ViewPager2 viewPager = view.findViewById(R.id.pager);
        TimetablePagerAdapter adapter = new TimetablePagerAdapter(this);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager, this).attach();
    }

    @Override
    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
        int semInt = position + 1;
        String text = Semester.fromInt(semInt).toString();
        tab.setText(text);
    }
}
