package com.includehelp.machinetesttask.api.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    //To get TimeZone List
    @GET("api/timezone/{continent}")
    Call<String> getTimeZoneList(@Path("continent") String continent);

    //To get TimeZone data
    @GET("api/timezone/{timezone}")
    Call<String> getTimeZoneData(@Path("timezone") String timezone);
}
