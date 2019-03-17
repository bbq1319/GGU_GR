package com.ggu.gguri;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ggu.gguri.databinding.FragmentGgusideMenuBinding;
import com.ggu.gguri.pojo.GetMember;
import com.ggu.gguri.pojo.LoginInfo;

import static com.ggu.gguri.R.id.frame;


/**
 * A simple {@link Fragment} subclass.
 */
public class GGUSideMenuFragment extends Fragment implements View.OnClickListener {

    FragmentGgusideMenuBinding binding;
    GGUNoticeFragment gguNoticeFragment = new GGUNoticeFragment();
    GGULoginFragment gguLoginFragment = new GGULoginFragment();
    MainActivity mainActivity = new MainActivity();

    Fragment fragment = null;
    String URL, name, stuno, major, major2;

    public GGUSideMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gguside_menu, container, false);
        View v = binding.getRoot();

        mainActivity.setActionBarTitle(getResources().getString(R.string.more), getResources().getColor(R.color.colorBlack), 24);

        binding.sideSchool.setOnClickListener(this);
        binding.sideDepartment.setOnClickListener(this);
        binding.sideNotice.setOnClickListener(this);
        binding.sideFree.setOnClickListener(this);
        binding.sideAnonymous.setOnClickListener(this);
        binding.sideCast.setOnClickListener(this);
        binding.sideDelivery.setOnClickListener(this);
        binding.sideMenu.setOnClickListener(this);
        binding.sideBus.setOnClickListener(this);

//        info = LoginInfo.getInstance();
//        System.out.println(info.getSex());
//        GetMember memberResponse = new GetMember();
//        System.out.println(memberResponse);
//        if(memberResponse.getData() != null) {
//            System.out.println(memberResponse.getData());
//            if(memberResponse.getData().getLoginInfo() != null) {
//                System.out.println(memberResponse.getData().getLoginInfo());
//
//            }
//        }

        // 로그인 시, 유저 정보 세팅
        if(gguLoginFragment.memberInfo == null) {
            System.out.println("null 입니다.");
        } else {
            name = gguLoginFragment.memberInfo.get("name").toString();
            stuno = gguLoginFragment.memberInfo.get("stuno").toString();
            major = gguLoginFragment.memberInfo.get("major").toString();

            binding.memberName.setText(name);
            binding.memberStuno.setText(stuno);
            if(TextUtils.isEmpty(major)){
                major2 = gguLoginFragment.memberInfo.get("major2").toString();
                binding.memberMajor.setText(major + "/" + major2);
            }
            else
                binding.memberMajor.setText(major);
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.side_school) {
            gguNoticeFragment.URL = "https://www.ggu.ac.kr/sub01080101";
            fragment = new GGUNoticeFragment();
        } else if(v.getId() == R.id.side_department) {
            gguNoticeFragment.URL = "https://www.ggu.ac.kr/sub050501";
            fragment = new GGUNoticeFragment();
        } else if(v.getId() == R.id.side_notice) {
            fragment = new GGUNoticeFragment();
        } else if(v.getId() == R.id.side_free) {
            fragment = new GGUNoticeFragment();
        } else if(v.getId() == R.id.side_anonymous) {
            fragment = new GGUNoticeFragment();
        } else if(v.getId() == R.id.side_cast) {
            fragment = new GGUNoticeFragment();
        } else if(v.getId() == R.id.side_delivery) {
            fragment = new GGUDeliveryFragment();
        } else if(v.getId() == R.id.side_menu) {
            fragment = new GGUMenuFragment();
        } else if(v.getId() == R.id.side_bus) {
            fragment = new GGUBusFragment();
        }

        if (fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.replace(frame, fragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            ft.commit();
        }
    }
}
