package com.ahmetardakavakci.coinlister.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.ahmetardakavakci.coinlister.R;
import com.ahmetardakavakci.coinlister.adapter.CoinAdapter;
import com.ahmetardakavakci.coinlister.databinding.ActivityMainBinding;
import com.ahmetardakavakci.coinlister.model.Coin;
import com.ahmetardakavakci.coinlister.service.CryptoAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    final private String BASE_URL = "https://gist.githubusercontent.com/";
    Retrofit retrofit;
    CoinAdapter coinAdapter;
    ArrayList<Coin> coinModels;

    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Gson gson = new GsonBuilder().setLenient().create();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(binding.recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        loadData();

        /*
         currencies json
         https://gist.githubusercontent.com/dybdeskarphet/3ef094a6c1adc9f5fbd3acc002ebf31f/raw/00bce6439636d103f88a4373c83edfc2c92bf946/currencies.json
        */
    }

    private void loadData() {

        CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);
        Call<List<Coin>> call = cryptoAPI.getData();

        call.enqueue(new Callback<List<Coin>>() {
            @Override
            public void onResponse(Call<List<Coin>> call, Response<List<Coin>> response) {
                if (response.isSuccessful()) {
                    List<Coin> responseList = response.body();
                    coinModels = new ArrayList<>(responseList);

                    coinAdapter = new CoinAdapter(coinModels);
                    binding.recyclerView.setAdapter(coinAdapter);

                    for (Coin coin : coinModels) {
                        System.out.println("Name: " + coin.currency + "\nPrice: " + coin.price + "\n---------------------");
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Coin>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

}