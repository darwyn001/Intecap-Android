package com.example.intecap.views;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.intecap.database.DatabaseHelper;
import com.example.intecap.databinding.FragmentDetailBinding;
import com.example.intecap.interfaces.OnDismissListener;
import com.example.intecap.models.Provider;
import com.example.intecap.utils.BitmapUtils;

public class DetailFragment extends DialogFragment {
    private final Provider provider;
    private final OnDismissListener onDismissListener;

    public DetailFragment(Provider provider, OnDismissListener onDismissListener) {
        this.provider = provider;
        this.onDismissListener = onDismissListener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentDetailBinding binding = FragmentDetailBinding.inflate(inflater, container, false);

        this.setCancelable(false);

        binding.textViewIdProviderDetail.setText(String.valueOf(provider.getId()));
        binding.textViewNameProviderDetail.setText(provider.getName());
        binding.textViewAddressProviderDetail.setText(provider.getAddress());
        binding.textViewProductProviderDetail.setText(provider.getProduct());
        binding.imageView2.setImageBitmap(BitmapUtils.getBitmapFromPath(provider.getPicturePath()));

        binding.buttonDelete.setOnClickListener(v -> new AlertDialog.Builder(requireContext())
                .setTitle("Confirmación")
                .setMessage("Esta seguro de quere borrar el proveedor " + provider.getName())
                .setPositiveButton("Sí", (dialog, which) -> {
                    DatabaseHelper.getInstance(requireContext()).delete(provider.getId());
                    onDismissListener.onDismissListener(this);
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show());

        binding.buttonClose.setOnClickListener(v -> this.dismiss());

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        requireDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }
}