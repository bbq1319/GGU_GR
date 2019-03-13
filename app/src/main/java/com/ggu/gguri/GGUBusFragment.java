package com.ggu.gguri;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ggu.gguri.databinding.FragmentGgubusBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class GGUBusFragment extends Fragment {

    GetBusTime getBusTime = new GetBusTime();
    CommonUtil commonUtil = new CommonUtil();
    MainActivity mainActivity = new MainActivity();

    String now = "";
    int ter_length, sch_length;

    TextView[] termArr;
    TextView[] schoolArr;

    public GGUBusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentGgubusBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ggubus, container, false);
        View v = binding.getRoot();

        mainActivity.setActionBarTitle(getResources().getString(R.string.bus));

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

        sch_length = schoolArr.length;
        ter_length = termArr.length;

        for(int i=0; i<sch_length; i++)
            schoolArr[i].setText(getBusTime.schoolTime[i]);
        for(int j=0; j<ter_length; j++)
            termArr[j].setText(getBusTime.termTime[j]);

        now = commonUtil.getCurTime("HH") + ":" + commonUtil.getCurTime("mm");

        schoolArr[getBusTime.getCurrentSTT(now)].setTextColor(getResources().getColor(R.color.colorRed));
        termArr[getBusTime.getCurrentTTS(now)].setTextColor(getResources().getColor(R.color.colorRed));

        binding.gguBus.setOnClickListener(v1 -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ggu.ac.kr/sub050501"));
            startActivity(intent);
        });
        binding.schToTer.setOnClickListener(v12 -> {
            binding.schToTer.setTextColor(getResources().getColor(R.color.colorWhite));
            binding.schToTer.setBackgroundColor(getResources().getColor(R.color.colorBlue));
            binding.terToSch.setTextColor(getResources().getColor(R.color.unPointColor));
            binding.terToSch.setBackgroundColor(getResources().getColor(R.color.none));
            binding.schoolList.setVisibility(View.VISIBLE);
            binding.terminalList.setVisibility(View.GONE);
        });
        binding.terToSch.setOnClickListener(v13 -> {
            binding.schToTer.setTextColor(getResources().getColor(R.color.unPointColor));
            binding.schToTer.setBackgroundColor(getResources().getColor(R.color.none));
            binding.terToSch.setTextColor(getResources().getColor(R.color.colorWhite));
            binding.terToSch.setBackgroundColor(getResources().getColor(R.color.colorBlue));
            binding.schoolList.setVisibility(View.GONE);
            binding.terminalList.setVisibility(View.VISIBLE);
        });

        return v;
    }

}
