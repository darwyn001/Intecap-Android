package com.example.intecap.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intecap.databinding.ItemRvProviderBinding;
import com.example.intecap.interfaces.OnItemClickListener;
import com.example.intecap.models.Provider;

import java.util.ArrayList;

public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.ViewHolder> {

    private final OnItemClickListener onItemClickListener;
    private final ArrayList<Provider> dataList;

    public ProviderAdapter(ArrayList<Provider> dataList, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemRvProviderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Provider actualProvider = dataList.get(position);
        holder.idProvider.setText(String.valueOf(actualProvider.getId()));
        holder.nameProvider.setText(actualProvider.getName());
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClickListener(dataList.get(position)));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void updateData(ArrayList<Provider> dataList){
        this.dataList.clear();
        this.dataList.addAll(dataList);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView idProvider;
        private final TextView nameProvider;

        public ViewHolder(@NonNull ItemRvProviderBinding itemBinding) {
            super(itemBinding.getRoot());
            this.idProvider = itemBinding.textViewIdProvider;
            this.nameProvider = itemBinding.textViewNameProvider;
        }
    }
}
