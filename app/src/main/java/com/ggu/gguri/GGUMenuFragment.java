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


/**
 * A simple {@link Fragment} subclass.
 */
public class GGUMenuFragment extends Fragment implements View.OnClickListener{

    FragmentGgumenuBinding binding;
    Fragment fragment = null;

    GetMenuList getMenuList = new GetMenuList();

    public GGUMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ggumenu, container, false);
        View v = binding.getRoot();

        getMenuList.doInBackground(getActivity());

        return v;
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
