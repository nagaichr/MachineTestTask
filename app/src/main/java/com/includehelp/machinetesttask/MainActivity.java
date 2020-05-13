package com.includehelp.machinetesttask;

import android.os.Bundle;
import android.view.View;

import com.includehelp.machinetesttask.databinding.ActivityMainBinding;
import com.includehelp.machinetesttask.fragments.LoginFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //view binding implemented
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Add Login Fragment
        LoginFragment loginFragment=new LoginFragment();
        setFragment(loginFragment);
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1) {
            finish();
        }
        else {
            getSupportFragmentManager().popBackStack();
        }
    }

    //To set Fragment
    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contentContainer,fragment,"Fragment");
        fragmentTransaction.addToBackStack("Fragment");
        fragmentTransaction.commit();

    }
}
