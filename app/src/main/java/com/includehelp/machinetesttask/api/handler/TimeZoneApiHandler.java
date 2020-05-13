package com.includehelp.machinetesttask.api.handler;

import android.util.Log;

import com.includehelp.machinetesttask.api.RetrofitClient;
import com.includehelp.machinetesttask.api.interfaces.ApiInterface;
import com.includehelp.machinetesttask.api.interfaces.ResultListenerInterface;
import com.includehelp.machinetesttask.util;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeZoneApiHandler {
    public static final String TAG= TimeZoneApiHandler.class.getSimpleName();
    ResultListenerInterface mTimeZoneResultListenerInterface;

    public TimeZoneApiHandler(ResultListenerInterface mTimeZoneResultListenerInterface) {
        this.mTimeZoneResultListenerInterface = mTimeZoneResultListenerInterface;
    }


    //For TimeZone
    public void getTimeZoneData(String timezone){
        ApiInterface apiInterface= RetrofitClient.getAPiService();

        Call<String> loginService= apiInterface.getTimeZoneList(timezone);
        loginService.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.e(TAG,"onResponse Code : "+response.code());
                    Log.e(TAG,"onResponse Body: "+response.body());

                    if(response.isSuccessful() && response.code()== HttpURLConnection.HTTP_OK){
                        String res = response.body();

                        mTimeZoneResultListenerInterface.onSuccess(util.TIMEZONE,res);
                    }
                    else {
                        mTimeZoneResultListenerInterface.onFail(util.TIMEZONE,"TimeZoneData Response is not SuccessFull!!\nResponse Code: "+response.code());
                    }
                }
                catch (Exception E){
                    mTimeZoneResultListenerInterface.onFail(util.TIMEZONE,"TimeZoneData Exception : "+E.getMessage());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG,"TimeZone onFailure : "+t.getMessage());
                mTimeZoneResultListenerInterface.onFail(util.TIMEZONE,"TimeZoneData onFailure: "+t.getMessage());
            }
        });
    }
}
