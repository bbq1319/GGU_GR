package com.ggu.gguri;


import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ggu.gguri.databinding.FragmentGgubusBinding;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class GGUBusFragment extends Fragment {

    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
    String now = format.format(new Date());

    String[] termTime = {
            "06:25", "07:25", "08:15", "09:30", "10:45", "12:00", "13:00",
            "14:00", "15:30", "17:00", "18:30", "19:30", "21:00"
    };
    String[] schoolTime = {
            "07:00", "08:20", "09:10", "10:25", "11:40", "12:55", "14:00",
            "15:00", "16:25", "18:00", "19:20", "20:20", "21:45"
    };

    TextView[] termArr;
    TextView[] schoolArr;

    int termArrLength;
    int schoolArrLength;

    public GGUBusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentGgubusBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ggubus, container, false);
        View v = binding.getRoot();

        termArr = new TextView[]{
                binding.termTime1, binding.termTime2, binding.termTime3, binding.termTime4,
                binding.termTime5, binding.termTime6, binding.termTime7, binding.termTime8,
                binding.termTime9, binding.termTime10, binding.termTime11, binding.termTime12,
                binding.termTime13
        };
        schoolArr = new TextView[]{
                binding.schoolTime1, binding.schoolTime2, binding.schoolTime3, binding.schoolTime4,
                binding.schoolTime5, binding.schoolTime6, binding.schoolTime7, binding.schoolTime8,
                binding.schoolTime9, binding.schoolTime10, binding.schoolTime11, binding.schoolTime12,
                binding.schoolTime13
        };

        termArrLength = termArr.length;
        schoolArrLength = schoolArr.length;

        binding.busTime.setText(now);

        binding.tabHost.setup();
        binding.tabHost.addTab(binding.tabHost.newTabSpec("Tab Spec 1").setIndicator("덕성여객").setContent(binding.tab1.getId()));
        binding.tabHost.addTab(binding.tabHost.newTabSpec("Tab Spec 2").setIndicator("학교버스").setContent(binding.tab2.getId()));

        binding.termToSchool.setText("터미널 -> 학교");
        binding.schoolToTerm.setText("학교 -> 터미널");

        for(int i=0; i<termArrLength; i++) {
            termArr[i].setText(termTime[i]);
            schoolArr[i].setText(schoolTime[i]);
        }

        for(int i=0; i<termArrLength; i++) {
            if(now.compareTo(termTime[i]) < 0){
                termArr[i].setTextColor(Color.RED);
                break;
            }
        }
        for(int i=0; i<schoolArrLength; i++) {
            if(now.compareTo(schoolTime[i]) < 0){
                schoolArr[i].setTextColor(Color.RED);
                break;
            }
        }

        return v;
    }

}
