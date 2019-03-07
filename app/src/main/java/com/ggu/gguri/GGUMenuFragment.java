package com.ggu.gguri;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ggu.gguri.databinding.FragmentGgumenuBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;

import static com.ggu.gguri.R.id.frame;
import static com.ggu.gguri.R.id.gone;
import static com.ggu.gguri.R.id.time;


/**
 * A simple {@link Fragment} subclass.
 */
public class GGUMenuFragment extends Fragment implements View.OnClickListener{

    FragmentGgumenuBinding binding;
    ProgressDialog asyncDialog;
    Fragment fragment = null;

    String[] week = {"일", "월", "화", "수", "목", "금", "토"};

    SimpleDateFormat curDate, curDay, curHour;
    String getNowDate, getNowDay, getNowHour;
    Date now = new Date();

    int getMonth, getDay, int_meal_time;
    String getMM, getDD, getToday;

    public GGUMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ggumenu, container, false);
        View v = binding.getRoot();

        // 로딩 창
        asyncDialog = ProgressDialog.show(
                getActivity(), "MyProgressBarTitle", "잠시만 기다려주세요...", true, false
        );

        binding.menuTimetable.setOnClickListener(this);
        binding.menuBreakfast.setOnClickListener(this);
        binding.menuLunch.setOnClickListener(this);
        binding.menuDinner.setOnClickListener(this);

        // 데이터 통신
        Observable.fromCallable(() -> {
            HashMap result = new HashMap();

            try {
                // 파싱 할 url 설정
                String strUrl = "https://www.ggu.ac.kr/sub0504/";
                Document doc = Jsoup.connect(strUrl).get();

                // 파싱 해오기
                Elements date = doc.select(".table-wrap").eq(1).select("table > tbody > tr > th");
                Elements day = doc.select(".table-wrap").eq(1).select("table > tbody > tr > td");

                // 시간 설정
                System.out.println(getToday());
                setText(binding.mainMenuDay, getToday());

                // 날짜 전달
                for (int i = 0; i < date.size(); i++) {
                    // 오늘 날 찾기( ex) 1/3 )
                    if ((getMonth + "/" + getDay + " " + "[" + getNowDay + "]").equals(date.get(i).text())) {

                        // 날짜 설정
                        result.put("date", date.get(i).text());

                        // 조식 / 메뉴 설정
                        if (0 <= int_meal_time && int_meal_time < 10) {
                            result.put("meal", date.get(0).text());
                            result.put("main", day.get(i * 3 - 3).text());
                        }

                        // 중식 / 메뉴 설정
                        else if (10 <= int_meal_time && int_meal_time < 14) {
                            result.put("meal", date.get(1).text());
                            result.put("main", day.get(i * 3 - 2).text());
                        }

                        // 석식 / 메뉴 설정
                        else if (14 <= int_meal_time && int_meal_time < 24) {
                            result.put("meal", date.get(2).text());
                            result.put("main", day.get(i * 3 - 1).text());
                        }
                    }
                    // 오늘 날 찾기( ex) 01/03 )
                    else if ((getMM + "/" + getDD + " " + "[" + getNowDay + "]").equals(date.get(i).text())) {

                        // 날짜 설정
                        result.put("date", date.get(i).text());

                        // 조식 / 메뉴 설정
                        if (0 <= int_meal_time && int_meal_time < 10) {
                            result.put("meal", date.get(0).text());
                            result.put("main", day.get(i * 3 - 3).text());
                        }

                        // 중식 / 메뉴 설정
                        else if (10 <= int_meal_time && int_meal_time < 14) {
                            result.put("meal", date.get(1).text());
                            result.put("main", day.get(i * 3 - 2).text());
                        }

                        // 석식 / 메뉴 설정
                        else if (14 <= int_meal_time && int_meal_time < 24) {
                            result.put("meal", date.get(2).text());
                            result.put("main", day.get(i * 3 - 1).text());
                        }
                    }

                }

                // 로딩 중
                for (int i = 0; i < 1; i++) {
                    Thread.sleep(500);
                }


            } catch (Exception e) {
                e.printStackTrace();

            }

            return result;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new DefaultObserver<Map>() {
                    @Override
                    public void onNext(Map map) {
                        onPostExecute(map);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Error","Error");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return v;
    }

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
    }

    public String getToday() {
        curDate = new SimpleDateFormat("MM/dd");
        curDay = new SimpleDateFormat("E");
        curHour = new SimpleDateFormat("HH");
        getNowDate = curDate.format(now);
        getNowDay = curDay.format(now);
        getNowHour = curHour.format(now);

        // 월, 일 구하기
        // MM/dd 형식과 M/d 형식으로 구분
        String[] dates = getNowDate.split("[/ ]");

        // M/d 형식
        if (dates.length > 1) {
            getMonth = Integer.valueOf(dates[0]);
            getDay = Integer.valueOf(dates[1]);
        } else {
            getMonth = 0;
            getDay = 0;
        }



        // MM/dd 형식
        getMM = dates[0];
        getDD = dates[1];

        // 시간 int형으로 변경
        int_meal_time = Integer.parseInt(getNowHour);

        // 날짜 한국어로 변경
        Calendar cal = Calendar.getInstance();
        getNowDay = week[cal.get(Calendar.DAY_OF_WEEK) - 1];

        getToday = getNowDate + "(" + getNowDay + ")";

        return getToday;
    }

    private void setText(final TextView text, final String value) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });

    }

    @Override
    public void onClick(View v) {
        LinearLayout timeTable = getActivity().findViewById(R.id.menu_timetable);
        int mealLength = timeTable.getChildCount();

        for(int i=0; i<mealLength; i++) {
            TextView a = timeTable.getChildAt(i).findViewById(timeTable.getChildAt(i).getId());
            if(timeTable.getChildAt(i).getId() == v.getId()) {
                a.setTextColor(getResources().getColor(R.color.colorBlue));
            }
            else {
                a.setTextColor(getResources().getColor(R.color.unPointColor));
            }
        }
    }

}
