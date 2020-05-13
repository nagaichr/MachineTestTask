package com.includehelp.machinetesttask.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.includehelp.machinetesttask.databinding.FragmentProfileBinding;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    public static final String TAG= ProfileFragment.class.getSimpleName();
    FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //View binding implementation
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        try{
            BufferedReader reader=new BufferedReader(new InputStreamReader(getResources().getAssets().open("User.json")));
            StringBuilder stringBuilder=new StringBuilder();
            String line;
            while ((line=reader.readLine())!=null){
                stringBuilder.append(line);
            }

            String json=stringBuilder.toString();
            JSONObject jsonObject= new JSONObject(json);

            binding.tvUsername.setText("User Name :  "+jsonObject.getString("UserName"));
            binding.tvProfilename.setText("Profile Name :  "+jsonObject.getString("ProfileName"));
            binding.tvEmail.setText("Email :  "+jsonObject.getString("Email"));
            binding.tvAge.setText("Age :  "+jsonObject.getString("Age"));
        }
        catch (Exception E){
            Log.e(TAG,"Profile Exp : "+E.toString());

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
