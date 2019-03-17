package com.ggu.gguri;


import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ggu.gguri.databinding.FragmentGguhomeBinding;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.ggu.gguri.R.id.frame;

/**
 * A simple {@link Fragment} subclass.
 */
public class GGUHomeFragment extends Fragment implements View.OnClickListener {

    FragmentGguhomeBinding binding;

    GetMenuList getMenuList = new GetMenuList();
    GetBusTime getBusTime = new GetBusTime();
    CommonUtil commonUtil = new CommonUtil();
    MainActivity mainActivity = new MainActivity();

    Timer timer;
    TimerTask timerTask;

    String today, now;
    Bitmap banner;
    int banners[] = {
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3
    };

    public GGUHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gguhome, container, false);
        View v = binding.getRoot();

        mainActivity.setActionBarTitle(getResources().getString(R.string.app_name), getResources().getColor(R.color.colorMain), 28);

        // onClick 세팅
        binding.mainGoMenu.setOnClickListener(this);
        binding.mainMenuBreakfast.setOnClickListener(this);
        binding.mainMenuLunch.setOnClickListener(this);
        binding.mainMenuDinner.setOnClickListener(this);
        binding.mainGoBus.setOnClickListener(this);

        // Viewpager 세팅
        binding.mainBanner.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return banners.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(getActivity());

                banner = BitmapFactory.decodeResource(getActivity().getResources(), banners[position]);

                imageView.setImageBitmap(banner);
                container.addView(imageView, 0);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((ImageView) object);
            }
        });
        timerTask = new TimerTask() {
            @Override
            public void run() {
                binding.mainBanner.post(() -> binding.mainBanner.setCurrentItem((binding.mainBanner.getCurrentItem()+1)%banners.length));
            }
        };
        binding.mainBanner.setCurrentItem(0);
        timer = new Timer();
        timer.schedule(timerTask, 5000, 5000);


        // TextView 세팅
        getMenuList.doInBackground(getActivity(), map -> onPostExecute(map));

        // 버스 시간표 세팅
        now = commonUtil.getCurTime("HH") + ":" + commonUtil.getCurTime("mm");
        binding.mainBusTerminal.setText(getBusTime.termTime[getBusTime.getCurrentTTS(now)]);
        binding.mainBusSchool.setText(getBusTime.schoolTime[getBusTime.getCurrentSTT(now)]);

        return v;
    }

    public void onPostExecute(Map map) {
        // 오늘 날짜 세팅
        today = commonUtil.getCurTime("MM") + "/" + commonUtil.getCurTime("dd") + "(" + commonUtil.transKorWeek() + ")";
        commonUtil.setText(getActivity(), binding.mainMenuDay, today);

        // 식단표에 메뉴가 있는지 없는지 확인
        if(map.get("today") == null) {
            // 메인 메뉴 오류 출력
            commonUtil.setText(getActivity(), binding.mainMenuBreakfast, getActivity().getResources().getString(R.string.menu_error));
            commonUtil.setText(getActivity(), binding.mainMenuLunch, getActivity().getResources().getString(R.string.menu_error));
            commonUtil.setText(getActivity(), binding.mainMenuDinner, getActivity().getResources().getString(R.string.menu_error));
        } else {
            // 메인 메뉴 출력
            String breakfastList = map.get("cur_breakfast").toString().replaceAll("\\s", "\n");
            String lunchList = map.get("cur_lunch").toString().replaceAll("\\s", "\n");
            String dinnerList = map.get("cur_dinner").toString().replaceAll("\\s", "\n");

            commonUtil.setText(getActivity(), binding.mainMenuBreakfast, breakfastList);
            commonUtil.setText(getActivity(), binding.mainMenuLunch, lunchList);
            commonUtil.setText(getActivity(), binding.mainMenuDinner, dinnerList);
        }
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;

        if(view.getId() == R.id.main_go_menu) {
            fragment = new GGUMenuFragment();
        } else if(view.getId() == R.id.main_go_bus) {
            fragment = new GGUBusFragment();
        }

        if (fragment != null) {
            String fragmentTag = fragment.getClass().getSimpleName();
            getFragmentManager().popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.replace(frame, fragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            ft.commit();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("task timer cancel");
        timer.cancel();
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }
}
