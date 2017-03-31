package com.example.ian_sibner.nytsearch.api;

import com.example.ian_sibner.nytsearch.models.Filters;
import com.example.ian_sibner.nytsearch.models.NytSearchApiResponse;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class NytSearchApi {
    private static final String API_KEY = "e2e125e585314fdd4fa5845ac6afda45:4:74222768";
    private static final String BASE_URL = "https://api.nytimes.com/svc/search/v2/";
    private static final Retrofit retrofit = getRetrofitInstance();

    private static Retrofit getRetrofitInstance() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder()
                        .addQueryParameter("api-key", API_KEY)
                        .build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        };
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static void search(Filters filters, Callback<NytSearchApiResponse> cb) {
        NytSearchApiService service = retrofit.create(NytSearchApiService.class);
        Call<NytSearchApiResponse> call = service.getSearchResults(filters.queryMap());
        call.enqueue(cb);
    }
}
