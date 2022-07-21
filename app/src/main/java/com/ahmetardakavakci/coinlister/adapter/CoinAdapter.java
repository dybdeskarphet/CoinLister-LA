package com.ahmetardakavakci.coinlister.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmetardakavakci.coinlister.databinding.RecyclerRowBinding;
import com.ahmetardakavakci.coinlister.model.Coin;

import java.util.ArrayList;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.CoinHolder>{

    private String[] colors = {"#791E94","#DE6449","#407899","#0F0326","#E65F5C","#44355B","#EE5622","#88A096","#D90429","#895737"};

    private ArrayList<Coin> coinModels;

    public CoinAdapter(ArrayList<Coin> coinModels) {
        this.coinModels = coinModels;
    }

    @NonNull
    @Override
    public CoinHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CoinHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinHolder holder, int position) {
        holder.itemView.setBackgroundColor(Color.parseColor(colors[position % 10]));
        holder.binding.nameText.setText(coinModels.get(position).currency);
        holder.binding.priceText.setText(coinModels.get(position).price);
    }

    @Override
    public int getItemCount() {
        return coinModels.size();
    }

    public class CoinHolder extends RecyclerView.ViewHolder {

        private RecyclerRowBinding binding;

        public CoinHolder(@NonNull RecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
