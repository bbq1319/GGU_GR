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

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class GGUMenuFragment extends Fragment implements View.OnClickListener{

    FragmentGgumenuBinding binding;

    GetMenuList getMenuList = new GetMenuList();
    CommonUtil commonUtil = new CommonUtil();

    LinearLayout timeTable, menuTable;
    TextView time, menu;
    String today;
    int mealLength;

    public GGUMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ggumenu, container, false);
        View v = binding.getRoot();

        // onClick 세팅
        binding.menuBreakfast.setOnClickListener(this);
        binding.menuLunch.setOnClickListener(this);
        binding.menuDinner.setOnClickListener(this);

        binding.mainMenuDayPrev.setOnClickListener(this);
        binding.mainMenuDayNext.setOnClickListener(this);

        // TextView 세팅
        getMenuList.doInBackground(getActivity(), map -> {
            System.out.println(map);
            onPostExecute(map);
        });

        return v;
    }

    public void onPostExecute(Map map) {
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
        commonUtil.setText(getActivity(), binding.mainMenuDayBottom, map.get("menu_map_day_0").toString());
        commonUtil.setText(getActivity(), binding.mainMenuBreakfast, map.get("menu_breakfast_0").toString());
        commonUtil.setText(getActivity(), binding.mainMenuLunch, map.get("menu_lunch_0").toString());
        commonUtil.setText(getActivity(), binding.mainMenuDinner, map.get("menu_dinner_0").toString());

        // Viewpager 세팅
//        binding.mainMenuBreakfast.setAdapter(new PagerAdapter() {
//
//            @Override
//            public int getCount() {
//                return banners.length;
//            }
//
//            @Override
//            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
//                return view == o;
//            }
//
//            @Override
//            public Object instantiateItem(ViewGroup container, int position) {
//                ImageView imageView = new ImageView(getActivity());
//
//                banner = BitmapFactory.decodeResource(getActivity().getResources(), banners[position]);
//
//                imageView.setImageBitmap(banner);
//                container.addView(imageView, 0);
//                return imageView;
//            }
//
//            @Override
//            public void destroyItem(ViewGroup container, int position, Object object) {
//                container.removeView((ImageView) object);
//            }
//
//        });
//        binding.mainBanner.setCurrentItem(0);
    }

    @Override
    public void onClick(View v) {
        // 상단 조식, 중식, 석식 클릭시 이벤트
        // 추후 모듈화
        timeTable = getActivity().findViewById(R.id.menu_timetable);
        menuTable = getActivity().findViewById(R.id.menu_table);
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

        // 금주의 식단 클릭 이벤트
    }

}
