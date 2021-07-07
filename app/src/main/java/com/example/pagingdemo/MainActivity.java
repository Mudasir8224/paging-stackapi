package com.example.pagingdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Call<StackApiResponse> call = RetrofitClient.getInstance()
                .getApi()
                .getAnswer(1,50,"stackoverflow");
        call.enqueue(new Callback<StackApiResponse>() {
            @Override
            public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse> response) {
                StackApiResponse apiResponse = response.body();
                if (apiResponse != null) {
                    Toast.makeText(MainActivity.this,String.valueOf(apiResponse.items.size()), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<StackApiResponse> call, Throwable t) {

            }
        });

    }
}