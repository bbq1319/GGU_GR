package com.ggu.gguri;

import com.ggu.gguri.pojo.GetMember;

import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("member/doLogin.json?")
    Call<GetMember> doLogin(
            @Query("id") String id,
            @Query("password") String password
    );

}
