package com.helloworld.presentation.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.helloworld.R;
import com.helloworld.databinding.ActivityLaunchesBinding;
import com.helloworld.databinding.ActivityMainBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LaunchesActivity extends AppCompatActivity {

    ActivityLaunchesBinding binding;
    private NavHostFragment navHostFragment = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLaunchesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupNavHostFragment();
    }

    private void setupNavHostFragment() {
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        navHostFragment.getNavController().addOnDestinationChangedListener((controller, destination, arguments) -> {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(destination.getLabel());
                actionBar.setDisplayHomeAsUpEnabled(destination.getId() == R.id.spaceLauncheDetail);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            navHostFragment.getNavController().popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}