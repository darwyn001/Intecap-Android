package com.example.intecap.views;

import android.content.Intent;
import android.os.Bundle;

import com.example.intecap.adapters.ProviderAdapter;
import com.example.intecap.database.DatabaseHelper;
import com.example.intecap.interfaces.OnItemClickListener;
import com.example.intecap.models.Provider;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.intecap.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.reciclerViewProviders.setLayoutManager(new LinearLayoutManager(this));
        binding.reciclerViewProviders.setAdapter(getAdapter());

        binding.fab.setOnClickListener(view -> startActivity(new Intent(this, CreateActivity.class)));

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateData();
    }

    private ProviderAdapter getAdapter() {

        ArrayList<Provider> dataList = DatabaseHelper.getInstance(this).selectAll();

        if (dataList != null && !dataList.isEmpty()) {
            binding.reciclerViewProviders.setVisibility(View.VISIBLE);
        } else {
            binding.textViewNoData.setVisibility(View.VISIBLE);
        }

        return new ProviderAdapter(
                DatabaseHelper.getInstance(this).selectAll(),
                (OnItemClickListener) provider -> new DetailFragment(provider, (d) -> {
                    d.dismiss();
                    updateData();
                }).show(getSupportFragmentManager(), "Detail")
        );
    }

    private  void updateData(){

        ProviderAdapter adapter = (ProviderAdapter) binding.reciclerViewProviders.getAdapter();
        Objects.requireNonNull(adapter).updateData(DatabaseHelper.getInstance(this).selectAll());
        adapter.notifyDataSetChanged();
    }
}