package com.impel.impelArticle.network;


import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller {
    public static final String BASE_URL = "https://newsapi.org/v2/";
    private static Retrofit retrofit = null;
    ApiService api;
    private static Controller controller;

    private Controller() {
        controller = this;
        getBaseClient();
        api = retrofit.create(ApiService.class);
    }

    public static Retrofit getBaseClient() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient();
            try {
                TLSSocketFactory tlsSocketFactory = new TLSSocketFactory();
                if (tlsSocketFactory.getTrustManager() != null) {
                    client = new OkHttpClient.Builder()
                            .sslSocketFactory(tlsSocketFactory, tlsSocketFactory.getTrustManager())
                            .connectTimeout(2, TimeUnit.MINUTES)
                            .readTimeout(2, TimeUnit.MINUTES)
                            .writeTimeout(2, TimeUnit.MINUTES)
                            .callTimeout(2, TimeUnit.MINUTES)
                            //.retryOnConnectionFailure(false)
                            /*.followRedirects(false)
                            .followSslRedirects(false)
                            .retryOnConnectionFailure(false)
                            cache(null)*/
                            .build();
                }
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
