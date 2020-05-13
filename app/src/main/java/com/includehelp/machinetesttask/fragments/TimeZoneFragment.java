package com.includehelp.machinetesttask.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.includehelp.machinetesttask.MainActivity;
import com.includehelp.machinetesttask.R;
import com.includehelp.machinetesttask.api.handler.ContinentApiHandler;
import com.includehelp.machinetesttask.api.handler.TimeZoneApiHandler;
import com.includehelp.machinetesttask.api.interfaces.ResultListenerInterface;
import com.includehelp.machinetesttask.databinding.FragmentTimezoneBinding;
import com.includehelp.machinetesttask.util;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import androidx.fragment.app.Fragment;

public class TimeZoneFragment extends Fragment implements View.OnClickListener, ResultListenerInterface {
    FragmentTimezoneBinding binding;
    ProgressDialog progress;
    TimeZoneApiHandler timeZoneApiHandler;

    public static final String TAG= ProfileFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //View binding implementation
        binding = FragmentTimezoneBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        binding.btnLogout.setOnClickListener(this);
        binding.btnProfile.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);
        progress = new ProgressDialog(getActivity());
        progress.setCancelable(false);
        progress.setMessage("Loading. . .");

        final ContinentApiHandler continentApiHandler = new ContinentApiHandler(this);
        binding.spinnerContinent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    Toast.makeText(getActivity(), parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    progress.show();
                    continentApiHandler.getTimeZone(parent.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "Nothing Selected", Toast.LENGTH_SHORT).show();
            }
        });
        timeZoneApiHandler = new TimeZoneApiHandler(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_profile:
                ProfileFragment profileFragment = new ProfileFragment();
                ((MainActivity) getActivity()).setFragment(profileFragment);
                break;
            case R.id.btn_logout:
                getActivity().onBackPressed();
                break;
            case R.id.btn_submit:
                try{
                    progress.show();
                    String timezone= binding.spinnerTimeZone.getSelectedItem().toString();
                    Toast.makeText(getActivity(), timezone, Toast.LENGTH_SHORT).show();
                    timeZoneApiHandler.getTimeZoneData(binding.spinnerTimeZone.getSelectedItem().toString());
                }
                catch (Exception E){
                    progress.dismiss();
                }
                break;
        }
    }

    @Override
    public void onSuccess(int code,String jsonData) {
        progress.dismiss();
        if(code== util.C0NTINENT){
            //creating Gson instance to convert JSON array to Java array
            Gson converter = new Gson();

            Type type = new TypeToken<List<String>>(){}.getType();
            List<String> list =  converter.fromJson(jsonData, type );


//            String[] timeZoneArray = jsonData.split(",");
//        ArrayList timezoneList = new ArrayList();
//        for(String s: timeZoneArray){
//            Log.e(TAG,s);
//            String[] zoneArr = s.split("/");
//            Log.e(TAG,zoneArr[1]);
//            timezoneList.add(zoneArr[1]);
//        }
            //create an ArrayAdaptar from the String Array
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_item, list);
            //set the view for the Drop down list
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //set the ArrayAdapter to the spinner
            binding.spinnerTimeZone.setAdapter(dataAdapter);
        }
        else if(code==util.TIMEZONE){
            try{
                Log.d(TAG,"Data : "+jsonData);

                JSONObject jsonObject = new JSONObject(jsonData);
                binding.tvLocaltime.setText("Time: "+jsonObject.getString("datetime"));
                binding.tvWeek.setText("DayOfWeek : "+jsonObject.getString("day_of_week"));
                binding.tvYear.setText("DayOfYear : "+jsonObject.getString("day_of_year"));
                binding.tvWeeknum.setText("Week Number : "+jsonObject.getString("week_number"));
            }
            catch (Exception E){
                Log.e(TAG,"Exp in Parsing : "+E.toString());
            }
        }
    }

    @Override
    public void onFail(int code,String message) {
        progress.dismiss();
        binding.tvErr.setText(message);
    }
}
