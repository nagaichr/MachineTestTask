package com.includehelp.machinetesttask.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.includehelp.machinetesttask.MainActivity;
import com.includehelp.machinetesttask.R;
import com.includehelp.machinetesttask.databinding.FragmentLoginBinding;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment implements View.OnClickListener {
    public static final String TAG= LoginFragment.class.getSimpleName();
    FragmentLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //View binding implementation
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        binding.btnLogin.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Login:
                String userName = binding.etUsername.getText().toString();
                String password = binding.etPassword.getText().toString();
                binding.tvMessage.setText("");
                performLogin(userName,password);
                break;
        }
    }

    void performLogin(String username, String password){
        try{
            if(username.equals("")){
                binding.tvMessage.setText("User name Can't be empty !!");
                return;
            }
            if(password.equals("")){
                binding.tvMessage.setText("Password Can't be empty !!");
                return;
            }

            BufferedReader reader=new BufferedReader(new InputStreamReader(getResources().getAssets().open("User.json")));
            StringBuilder stringBuilder=new StringBuilder();
            String line;
            while ((line=reader.readLine())!=null){
                stringBuilder.append(line);
            }

            String json=stringBuilder.toString();
            JSONObject jsonObject= new JSONObject(json);

            String user= jsonObject.getString("UserName");
            String pass= jsonObject.getString("Password");


            if(binding.etUsername.getText().toString().equals(user) &&
                    binding.etPassword.getText().toString().equals(pass) ) {

                TimeZoneFragment timeZoneFragment = new TimeZoneFragment();
                ((MainActivity) getActivity()).setFragment(timeZoneFragment);
            }
            else{
                binding.tvMessage.setText("InValid UserID and Password !!");
            }
        }
        catch (Exception E){
            Log.e(TAG,"Login Exp: "+E.getMessage());
            Toast.makeText(getActivity(), "Login Exp: "+E.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
