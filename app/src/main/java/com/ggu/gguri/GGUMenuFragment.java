package com.ggu.gguri;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ggu.gguri.databinding.FragmentGgumenuBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.ggu.gguri.R.id.frame;


/**
 * A simple {@link Fragment} subclass.
 */
public class GGUMenuFragment extends Fragment {

    FragmentGgumenuBinding binding;
    Fragment fragment = null;

    public GGUMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ggumenu, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ggumenu, container, false);
        View v = binding.getRoot();

        binding.tabHost.setup();
        binding.tabHost.addTab(binding.tabHost.newTabSpec("Tab Spec 1").setIndicator("오늘의 식단").setContent(binding.tab1.getId()));
        binding.tabHost.addTab(binding.tabHost.newTabSpec("Tab Spec 2").setIndicator("전체 식단표").setContent(binding.tab2.getId()));

        GetResult task = new GetResult();
        task.execute();

        return v;
    }

    private class GetResult extends AsyncTask<Void, Void, Map<String, String>> {

        ProgressDialog asyncDialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute(){
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("잠시만 기다려주세요...");

            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Map<String, String> doInBackground(Void... params) {
            HashMap result = new HashMap();

            try {
                // 파싱을 url 설정
                String strUrl = "https://www.ggu.ac.kr/sub0504/";
                Document doc = Jsoup.connect(strUrl).get();

                // 파싱 해오기
                Elements date = doc.select(".table-wrap > table > tbody > tr > th");
                Elements day = doc.select(".table-wrap > table > tbody > tr > td");

                // 시간 설정
                Date now = new Date();
                SimpleDateFormat curDate = new SimpleDateFormat("MM/dd");
                SimpleDateFormat curDay = new SimpleDateFormat("E");
                SimpleDateFormat curHour = new SimpleDateFormat("HH");
                String getNowDate = curDate.format(now);
                String getNowDay = curDay.format(now);
                String getNowHour = curHour.format(now);

                // 월, 일 구하기
                // MM/dd 형식과 M/d 형식으로 구분
                String[] dates = getNowDate.split("[/ ]");
                int getMonth;
                int getDay;

                // M/d 형식
                if (dates.length > 1) {
                    getMonth = Integer.valueOf(dates[0]);
                    getDay = Integer.valueOf(dates[1]);
                } else {
                    getMonth = 0;
                    getDay = 0;
                }

                // MM/dd 형식
                String getMM = dates[0];
                String getDD = dates[1];

                // 시간 int형으로 변경
                int int_meal_time = Integer.parseInt(getNowHour);

                // 날짜 한국어로 변경
                switch (getNowDay) {
                    case "Mon": getNowDay = "월";
                        break;
                    case "Tue": getNowDay = "화";
                        break;
                    case "Wed": getNowDay = "수";
                        break;
                    case "Thu": getNowDay = "목";
                        break;
                    case "Fri": getNowDay = "금";
                        break;
                    case "Sat": getNowDay = "토";
                        break;
                    case "Sun": getNowDay = "일";
                        break;
                }

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
        }

        @Override
        protected void onPostExecute(Map<String, String> map){

            asyncDialog.dismiss();

            try {
                // 날짜 / 조,중,석식 출력
                binding.menuDate.setText(map.get("date"));
                binding.menuDay.setText(map.get("meal"));

                // 메뉴 출력
                String getFoodMain = map.get("main");
                System.out.println(getFoodMain);
                String[] setFoodMain = getFoodMain.split("\\s");

                for(int n=0;n<setFoodMain.length;n++)
                    binding.menuList.append(setFoodMain[n]+"\n");

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

    }

}
