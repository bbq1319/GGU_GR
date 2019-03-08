package com.ggu.gguri;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ggu.gguri.databinding.FragmentGgumenuBinding;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class GGUMenuFragment extends Fragment implements View.OnClickListener{

    FragmentGgumenuBinding binding;
    Fragment fragment = null;

    GetMenuList getMenuList = new GetMenuList();
    CommonUtil commonUtil = new CommonUtil();

    public GGUMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ggumenu, container, false);
        View v = binding.getRoot();

        binding.menuBreakfast.setOnClickListener(this);
        binding.menuLunch.setOnClickListener(this);
        binding.menuDinner.setOnClickListener(this);

        // TextView 세팅
        getMenuList.doInBackground(getActivity(), map -> {
            System.out.println(map);
            onPostExecute(map);
        });

        return v;
    }

    public void onPostExecute(Map map) {
        int par_day_length = Integer.parseInt(map.get("par_day_length").toString());
        String getCurMealTime = getMenuList.getCurMealTime(getActivity());
        String breakfast = getActivity().getResources().getString(R.string.menu_breakfast);
        String lunch = getActivity().getResources().getString(R.string.menu_lunch);
        String dinner = getActivity().getResources().getString(R.string.menu_dinner);

        // 오늘 날짜 세팅
        commonUtil.setText(getActivity(), binding.mainMenuDayTop, map.get("today").toString());
        commonUtil.setText(getActivity(), binding.mainMenuDayBottom, map.get("today").toString());

        // 현재 시간에 따라 조식,중식,석시 세팅
        binding.menuBreakfast.setTextColor(getResources().getColor(R.color.unPointColor));
        binding.menuLunch.setTextColor(getResources().getColor(R.color.unPointColor));
        binding.menuDinner.setTextColor(getResources().getColor(R.color.unPointColor));
        commonUtil.setText(getActivity(), binding.menuBreakfastMeal, map.get("cur_breakfast").toString());
        commonUtil.setText(getActivity(), binding.menuLunchMeal, map.get("cur_lunch").toString());
        commonUtil.setText(getActivity(), binding.menuDinnerMeal, map.get("cur_dinner").toString());

        if(getCurMealTime == null || getCurMealTime == "") {
            Toast.makeText(getActivity().getApplicationContext(), "오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            return;
        }

        View view = binding.menuBreakfastMeal;

        if(getCurMealTime.equals(breakfast)) {
            binding.menuBreakfast.setTextColor(getResources().getColor(R.color.colorBlue));
            binding.menuBreakfastMeal.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            binding.menuBreakfastMeal.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            binding.menuBreakfastMeal.setVisibility(View.VISIBLE);
        }
        else if(getCurMealTime.equals(lunch)) {
            binding.menuLunch.setTextColor(getResources().getColor(R.color.colorBlue));
            binding.menuLunchMeal.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            binding.menuLunchMeal.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            binding.menuLunchMeal.setVisibility(View.VISIBLE);
        }
        else if(getCurMealTime.equals(dinner)) {
            binding.menuDinner.setTextColor(getResources().getColor(R.color.colorBlue));
            binding.menuDinnerMeal.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            binding.menuDinnerMeal.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            binding.menuDinnerMeal.setVisibility(View.VISIBLE);
        }

        // 금주의 식단 세팅
//        for(int i=0; i<par_day_length; i++) {
//            commonUtil.setText(getActivity(), binding.mainMenuBreakfast, map.get("p"));
//
//        }
    }
/*
    public void onPostExecute(Map<String, String> map){

        asyncDialog.dismiss();

        try {
            // 날짜 / 조,중,석식 출력
//                binding.menuDate.setText(map.get("date"));
//                binding.menuDay.setText(map.get("meal"));

            System.out.println("map.date" + map.get("date"));
            System.out.println("map.meal" + map.get("meal"));

            // 메뉴 출력
            String getFoodMain = "";
            getFoodMain = map.get("main");
            System.out.println(getFoodMain);
            if(getFoodMain != null || getFoodMain != "") {
            }


//                for(int n=0;n<setFoodMain.length;n++)
//                    binding.menuList.append(setFoodMain[n]+"\n");

        } catch (Exception e){
            e.printStackTrace();
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setTitle("경고");
            dialog.setMessage("예상치 못한 오류로 불러올 수 없습니다.")
                    .setCancelable(true)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fragment = new GGUHomeFragment();

                            if (fragment != null) {
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                ft.replace(frame, fragment);
                                ft.addToBackStack(null);
                                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                                ft.commit();
                            }
                        }
                    });
            dialog.show();

        }
    }*/

    @Override
    public void onClick(View v) {
        LinearLayout timeTable = getActivity().findViewById(R.id.menu_timetable);
        LinearLayout menuTable = getActivity().findViewById(R.id.menu_table);
        int mealLength = timeTable.getChildCount();

        for(int i=0; i<mealLength; i++) {
            TextView a = timeTable.getChildAt(i).findViewById(timeTable.getChildAt(i).getId());
            TextView b = timeTable.getChildAt(i).findViewById(menuTable.getChildAt(i).getId());
            if(timeTable.getChildAt(i).getId() == v.getId()) {
                a.setTextColor(getResources().getColor(R.color.colorBlue));
                b.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                b.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                b.setVisibility(View.VISIBLE);
            }
            else {
                a.setTextColor(getResources().getColor(R.color.unPointColor));
                b.setWidth(0);
                b.setHeight(0);
                b.setVisibility(View.GONE);
            }
        }
    }

}
