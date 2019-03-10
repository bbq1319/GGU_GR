package com.ggu.gguri;


import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;

public class GetMenuList {

    CommonUtil commonUtil = new CommonUtil();

    ProgressDialog dialog;
    String months = "MM";
    String month = "M";
    String days = "dd";
    String day = "d";
    String hour = "HH";

    interface onPostInterface{
        void onPostExecute(Map map);
    }

    public final void doInBackground(final Activity activity, onPostInterface callBack) {
        dialog = ProgressDialog.show(activity, "로딩 중", "잠시만 기다려주세요...", true, false);

         Observable.fromCallable(() -> {
            HashMap result = new HashMap();

            try {
                System.out.println("Observable in");
                String strUrl = "https://www.ggu.ac.kr/sub0504/";
                Document doc = Jsoup.connect(strUrl).get();

                // 파싱 해오기
                Elements par_row = doc.select(".table-wrap").eq(1).select("table > tbody > tr");
                Elements par_day = doc.select(".table-wrap").eq(1).select("table > tbody > tr > th");

                // 현재 메뉴 가져오기
                int par_day_length = par_day.size();
                String cur_date = commonUtil.getCurTime(month) + "/" + commonUtil.getCurTime(day);
                String cur_dates = commonUtil.getCurTime(months) + "/" + commonUtil.getCurTime(days);

                result.put("par_day_length", par_day_length);
                for(int i=0; i<par_day_length; i++) {
                    result.put("menu_map_" + i, par_row.get(i));
                    result.put("menu_map_day_" + i, par_day.get(i).text());
                    result.put("menu_breakfast_" + i, par_row.get(i).select("td").eq(0).text());
                    result.put("menu_lunch_" + i, par_row.get(i).select("td").eq(1).text());
                    result.put("menu_dinner_" + i, par_row.get(i).select("td").eq(2).text());

                    if(par_day.get(i).text().contains(cur_date) || par_day.get(i).text().contains(cur_dates)) {
                        result.put("today", par_day.get(i).text());
                        result.put("cur_breakfast", par_row.get(i).select("td").eq(0).text());
                        result.put("cur_lunch", par_row.get(i).select("td").eq(1).text());
                        result.put("cur_dinner", par_row.get(i).select("td").eq(2).text());
                    }
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
                                callBack.onPostExecute(map);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("Error","Error");
                            }

                            @Override
                            public void onComplete() {
                                dialog.dismiss();
                            }
                        });
    }

    // 현재 식사시간 가져오기
    public final String getCurMealTime(Activity activity) {
        int curHour = 0;
        String getCurTime = commonUtil.getCurTime(hour);
        String curMealTime = "";

        if(getCurTime == null) {
            Toast.makeText(activity.getApplicationContext(), "오류가 발생했습니다.", Toast.LENGTH_LONG).show();
        }
        else {
            curHour = Integer.parseInt(getCurTime);

            // 아침 설정
            if(curHour >= 0 && curHour < 10){
                curMealTime = activity.getResources().getString(R.string.menu_breakfast);
                return curMealTime;
            }
            // 점심 설정
            else if(curHour >= 10 && curHour < 14) {
                curMealTime = activity.getResources().getString(R.string.menu_lunch);
                return curMealTime;
            }
            // 저녁 설정
            else if(curHour >= 14 && curHour < 24) {
                curMealTime = activity.getResources().getString(R.string.menu_dinner);
                return curMealTime;
            }
        }

        return null;
    }

}
