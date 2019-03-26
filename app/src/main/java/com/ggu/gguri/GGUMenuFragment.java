package com.ggu.gguri;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ggu.gguri.databinding.FragmentGgumenuBinding;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class GGUMenuFragment extends Fragment{

    FragmentGgumenuBinding binding;
    GetMenuList getMenuList = new GetMenuList();
    CommonUtil commonUtil = new CommonUtil();

    LinearLayout timeTable, menuTable;
    TextView time, menu;
    String today, breakfastList, lunchList, dinnerList;
    String menu_map_day = "menu_map_day_";
    String menu_breakfast = "menu_breakfast_";
    String menu_lunch = "menu_lunch_";
    String menu_dinner = "menu_dinner_";
    Map result;
    int mealLength, curMenuPage;

    public GGUMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ggumenu, container, false);
        View v = binding.getRoot();

//        mainActivity.setActionBarTitle(getResources().getString(R.string.menu), getResources().getColor(R.color.colorBlack), 24);

        // onClick 세팅
        binding.menuBreakfast.setOnClickListener(this::todayButtonClick);
        binding.menuLunch.setOnClickListener(this::todayButtonClick);
        binding.menuDinner.setOnClickListener(this::todayButtonClick);

        binding.mainMenuDayPrev.setOnClickListener(this::weekButtonClick);
        binding.mainMenuDayNext.setOnClickListener(this::weekButtonClick);

        binding.gguMenu.setOnClickListener(this::goGGUMenu);

        // TextView 세팅
        getMenuList.doInBackground(getActivity(), map -> {
            System.out.println(map);
            onPostExecute(map);
        });

        return v;
    }

    public void onPostExecute(Map map) {
        result = map;
        curMenuPage = 0;
        String getCurMealTime = getMenuList.getCurMealTime(getActivity());
        String breakfast = getActivity().getResources().getString(R.string.menu_breakfast);
        String lunch = getActivity().getResources().getString(R.string.menu_lunch);
        String dinner = getActivity().getResources().getString(R.string.menu_dinner);

        // 오늘 날짜 세팅
        today = commonUtil.getCurTime("MM") + "/" + commonUtil.getCurTime("dd") + "(" + commonUtil.transKorWeek() + ")";
        commonUtil.setText(getActivity(), binding.mainMenuDayTop, today);

        // 글씨 색 초기화
        binding.menuBreakfast.setTextColor(getResources().getColor(R.color.unPointColor));
        binding.menuLunch.setTextColor(getResources().getColor(R.color.unPointColor));
        binding.menuDinner.setTextColor(getResources().getColor(R.color.unPointColor));

        // 식단표에 메뉴가 있는지 없는지 확인
        if(map.get("today") == null) {
            // 메인 메뉴 오류 출력
            commonUtil.setText(getActivity(), binding.menuBreakfastMeal, getActivity().getResources().getString(R.string.menu_error));
            commonUtil.setText(getActivity(), binding.menuLunchMeal, getActivity().getResources().getString(R.string.menu_error));
            commonUtil.setText(getActivity(), binding.menuDinnerMeal, getActivity().getResources().getString(R.string.menu_error));
        } else {
            // 현재 시간에 따라 조식,중식,석식 세팅
            commonUtil.setText(getActivity(), binding.menuBreakfastMeal, map.get("cur_breakfast").toString());
            commonUtil.setText(getActivity(), binding.menuLunchMeal, map.get("cur_lunch").toString());
            commonUtil.setText(getActivity(), binding.menuDinnerMeal, map.get("cur_dinner").toString());
        }

        // 현재 시간에 맞춰 글씨색 변경
        if(getCurMealTime == null || getCurMealTime == "") {
            Toast.makeText(getActivity().getApplicationContext(), "오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            return;
        }

        if(getCurMealTime.equals(breakfast)) {
            binding.menuBreakfast.setTextColor(getResources().getColor(R.color.colorBlue));
            binding.menuBreakfastMeal.setVisibility(View.VISIBLE);
        }
        else if(getCurMealTime.equals(lunch)) {
            binding.menuLunch.setTextColor(getResources().getColor(R.color.colorBlue));
            binding.menuLunchMeal.setVisibility(View.VISIBLE);
        }
        else if(getCurMealTime.equals(dinner)) {
            binding.menuDinner.setTextColor(getResources().getColor(R.color.colorBlue));
            binding.menuDinnerMeal.setVisibility(View.VISIBLE);
        }

        // 금주의 식단 세팅
        breakfastList = map.get(menu_breakfast + curMenuPage).toString().replaceAll("\\s", "\n");
        lunchList = map.get(menu_lunch + curMenuPage).toString().replaceAll("\\s", "\n");
        dinnerList = map.get(menu_dinner + curMenuPage).toString().replaceAll("\\s", "\n");
        System.out.println("week food list");
        System.out.println(breakfastList);

        commonUtil.setText(getActivity(), binding.mainMenuDayBottom, map.get("menu_map_day_0").toString());
        commonUtil.setText(getActivity(), binding.mainMenuBreakfast, breakfastList);
        commonUtil.setText(getActivity(), binding.mainMenuLunch, lunchList);
        commonUtil.setText(getActivity(), binding.mainMenuDinner, dinnerList);
    }

    // 상단 조식, 중식, 석식 클릭시 이벤트
    public void todayButtonClick(View v) {
        timeTable = binding.menuTimetable;
        menuTable = binding.menuTable;
        mealLength = timeTable.getChildCount();

        for(int i=0; i<mealLength; i++) {
            time = timeTable.getChildAt(i).findViewById(timeTable.getChildAt(i).getId());
            menu = menuTable.getChildAt(i).findViewById(menuTable.getChildAt(i).getId());
            if(timeTable.getChildAt(i).getId() == v.getId()) {
                time.setTextColor(getResources().getColor(R.color.colorBlue));
                menu.setVisibility(View.VISIBLE);
            }
            else {
                time.setTextColor(getResources().getColor(R.color.unPointColor));
                menu.setVisibility(View.GONE);
            }
        }
    }

    // 금주의 식단 클릭 이벤트
    public void weekButtonClick(View v) {
        if(v.getId() == binding.mainMenuDayPrev.getId() && curMenuPage > 0) {
            curMenuPage--;
            breakfastList = result.get(menu_breakfast + curMenuPage).toString().replaceAll("\\s", "\n");
            lunchList = result.get(menu_lunch + curMenuPage).toString().replaceAll("\\s", "\n");
            dinnerList = result.get(menu_dinner + curMenuPage).toString().replaceAll("\\s", "\n");

            commonUtil.setText(getActivity(), binding.mainMenuDayBottom, result.get(menu_map_day + curMenuPage).toString());
            commonUtil.setText(getActivity(), binding.mainMenuBreakfast, breakfastList);
            commonUtil.setText(getActivity(), binding.mainMenuLunch, lunchList);
            commonUtil.setText(getActivity(), binding.mainMenuDinner, dinnerList);
        }

        if(v.getId() == binding.mainMenuDayNext.getId() && curMenuPage < Integer.parseInt(result.get("par_day_length").toString()) - 2) {
            curMenuPage++;
            breakfastList = result.get(menu_breakfast + curMenuPage).toString().replaceAll("\\s", "\n");
            lunchList = result.get(menu_lunch + curMenuPage).toString().replaceAll("\\s", "\n");
            dinnerList = result.get(menu_dinner + curMenuPage).toString().replaceAll("\\s", "\n");

            commonUtil.setText(getActivity(), binding.mainMenuDayBottom, result.get(menu_map_day + curMenuPage).toString());
            commonUtil.setText(getActivity(), binding.mainMenuBreakfast, breakfastList);
            commonUtil.setText(getActivity(), binding.mainMenuLunch, lunchList);
            commonUtil.setText(getActivity(), binding.mainMenuDinner, dinnerList);
        }
    }

    public void goGGUMenu(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ggu.ac.kr/sub0504"));
        startActivity(intent);
    }

}
