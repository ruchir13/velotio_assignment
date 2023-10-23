package com.helloworld.data.remote;


import com.helloworld.domain.entities.Rocket;
import com.helloworld.domain.entities.SpacexLaunch;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

//API endpoints.
public interface RetrofitServiceInterface {
    String BASE_URL = "https://api.spacexdata.com/v3";
    @GET("/launches")
    Call<List<SpacexLaunch>> listLaunches();

    @GET("/rockets/{id}")
    Call<Rocket> getLaunchDetail(@Path("id") String id);

}
