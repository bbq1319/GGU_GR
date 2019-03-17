package com.ggu.gguri;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ggu.gguri.databinding.FragmentGguloginBinding;
import com.ggu.gguri.pojo.GetMember;

import org.jsoup.helper.StringUtil;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class GGULoginFragment extends Fragment implements View.OnClickListener {

    FragmentGguloginBinding binding;
    MainActivity mainActivity = new MainActivity();

    APIClient retrofit = new APIClient();
    APIInterface service;

    String id, pw;
    static HashMap memberInfo;

    public GGULoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ggulogin, container, false);
        View v = binding.getRoot();

        mainActivity.setActionBarTitle(getResources().getString(R.string.empty), getResources().getColor(R.color.none), 0);

        binding.login.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        retrofit.URL = "http://ggugguri.cafe24.com/";
        id = binding.id.getText().toString();
        pw = binding.password.getText().toString();
        service = retrofit.getClient().create(APIInterface.class);

        if(StringUtil.isBlank(id) && StringUtil.isBlank(pw)) {
            Toast.makeText(getActivity(), getResources().getText(R.string.empty_idpw), Toast.LENGTH_LONG).show();
        } else {
            Call<GetMember> request = service.doLogin(id, pw);
            request.enqueue(new Callback<GetMember>() {
                @Override
                public void onResponse(Call<GetMember> call, Response<GetMember> response) {
                    if(response.isSuccessful()) {
                        if(response.body().getData().getRESULTCODE().equals(0)) {
                            memberInfo = new HashMap();
                            memberInfo.put("id", response.body().getData().getLoginInfo().getId());
                            memberInfo.put("nicknm", response.body().getData().getLoginInfo().getNicknm());
                            memberInfo.put("name", response.body().getData().getLoginInfo().getName());
                            memberInfo.put("sex", response.body().getData().getLoginInfo().getSex());
//                            memberInfo.put("birth", response.body().getData().getLoginInfo().getId());
                            memberInfo.put("email", response.body().getData().getLoginInfo().getEmail());
                            memberInfo.put("major", response.body().getData().getLoginInfo().getMajor());
                            memberInfo.put("major2", response.body().getData().getLoginInfo().getMajor2());
                            memberInfo.put("stuno", response.body().getData().getLoginInfo().getStuno());
//                            memberInfo.put("regdate", response.body().getData().getLoginInfo().getId());
                        } else {
                            Toast.makeText(getActivity(), getResources().getText(R.string.wrong_idpw), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), getResources().getText(R.string.login_err), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<GetMember> call, Throwable t) {
                    call.cancel();
                    Toast.makeText(getActivity(), getResources().getText(R.string.login_err), Toast.LENGTH_LONG).show();
                    System.out.println(call);
                    System.out.println(t);
                }
            });
        }
    }
}
