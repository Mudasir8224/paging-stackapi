package com.example.pagingdemo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("answers")
    Call<StackApiResponse> getAnswer(
            @Query("page") int page,
            @Query("pagesize") int size,
            @Query("site") String site
    );

}
