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

public class ContinentApiHandler {
    public static final String TAG = ContinentApiHandler.class.getSimpleName();
    ResultListenerInterface resultListenerInterface;

    public ContinentApiHandler(ResultListenerInterface resultListenerInterface) {
        this.resultListenerInterface = resultListenerInterface;
    }


    //For TimeZone
    public void getTimeZone(String continent){
       ApiInterface apiInterface= RetrofitClient.getAPiService();

       Call<String> loginService= apiInterface.getTimeZoneList(continent);
       loginService.enqueue(new Callback<String>() {
           @Override
           public void onResponse(Call<String> call, Response<String> response) {
              try {
                  Log.e(TAG,"onResponse Code : "+response.code());
                  Log.e(TAG,"nResponse Body: "+response.body());

                  if(response.isSuccessful() && response.code()== HttpURLConnection.HTTP_OK){
                      String res = response.body();

                      resultListenerInterface.onSuccess(util.C0NTINENT,res);
                  }
                  else {
                      resultListenerInterface.onFail(util.C0NTINENT,"TimeZone Response is not SuccessFull!!\nResponse Code: "+response.code());
                  }
              }
              catch (Exception E){
                  resultListenerInterface.onFail(util.C0NTINENT,"TimeZone Exception : "+E.getMessage());
              }
           }

           @Override
           public void onFailure(Call<String> call, Throwable t) {
               Log.e(TAG,"TimeZone onFailure : "+t.getMessage());
               resultListenerInterface.onFail(util.TIMEZONE,"TimeZone onFailure: "+t.getMessage());
           }
       });
    }
}
