package com.example.pagingdemo;

import androidx.paging.PageKeyedDataSource;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDataSource extends PageKeyedDataSource<Integer,Item> {
    private static final int PAGE_SIZE = 50;
    private static final int FIRST_PAGE = 1;
    private static final String SITE_NAME = "stackoverflow";

    @Override
    public void loadAfter(@NotNull LoadParams<Integer> loadParams, @NotNull LoadCallback<Integer, Item> loadCallback) {
        RetrofitClient.getInstance()
                .getApi()
                .getAnswer(loadParams.key,PAGE_SIZE,SITE_NAME)
                .enqueue(new Callback<StackApiResponse>() {
                    @Override
                    public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse> response) {
                        Integer key = response.body().has_more ? loadParams.key +1 : null;

                        if (response.body() != null){
                            loadCallback.onResult(response.body().items,key);
                        }
                    }

                    @Override
                    public void onFailure(Call<StackApiResponse> call, Throwable t) {

                    }
                });

    }

    @Override
    public void loadBefore(@NotNull LoadParams<Integer> loadParams, @NotNull LoadCallback<Integer, Item> loadCallback) {
        RetrofitClient.getInstance()
                .getApi()
                .getAnswer(loadParams.key,PAGE_SIZE,SITE_NAME)
                .enqueue(new Callback<StackApiResponse>() {
                    @Override
                    public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse> response) {
                       Integer key = (loadParams.key> 1) ? loadParams.key -1 :null;

                       if (response.body() != null){
                           loadCallback.onResult(response.body().items,key);
                       }
                    }

                    @Override
                    public void onFailure(Call<StackApiResponse> call, Throwable t) {

                    }
                });

    }

    @Override
    public void loadInitial(@NotNull LoadInitialParams<Integer> loadInitialParams, @NotNull LoadInitialCallback<Integer, Item> loadInitialCallback) {

        RetrofitClient.getInstance()
                .getApi()
                .getAnswer(FIRST_PAGE,PAGE_SIZE,SITE_NAME)
                .enqueue(new Callback<StackApiResponse>() {
                    @Override
                    public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse> response) {
                        if (response.body() != null){
                            loadInitialCallback.onResult(response.body().items,null,FIRST_PAGE+1);
                        }
                    }

                    @Override
                    public void onFailure(Call<StackApiResponse> call, Throwable t) {

                    }
                });

    }
}
