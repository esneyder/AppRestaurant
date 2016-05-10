package com.developer.webapiclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tan on 3/4/2016.
 */
public class RestClient {
    //You need to change the IP if you testing environment is not local machine
    //or you may have different URL than we have here
    private static final String URL = "https://microsoft-apiapp454fa0f32a954e0a8fc8b56746f1a46a.azurewebsites.net/";
    private retrofit2.Retrofit restAdapter;
    private InstituteService apiService;

    public RestClient()
    {

        //https://github.com/square/okhttp/wiki/Interceptors
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Interceptor.Chain chain) throws IOException {
                                Request original = chain.request();

                                // Request customization: add request headers
                                Request.Builder requestBuilder = original.newBuilder()
                                        //.header("Host", "https://microsoft-apiapp454fa0f32a954e0a8fc8b56746f1a46a.azurewebsites.net/")
                                        .method(original.method(), original.body());

                                Request request = requestBuilder.build();
                                return chain.proceed(request);
                            }
                        })
                .build();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        restAdapter = new retrofit2.Retrofit.Builder()
                .baseUrl(URL)
//                .setLogLevel(retrofit2.Retrofit.LogLevel.FULL)
//                .setRequestInterceptor(new RequestInterceptor() {
//                    @Override
//                    public void intercept(RequestFacade requestFacade) {
//                        if (URL.contains("10.0.2.2"))
//                            requestFacade.addHeader("Host", "localhost");
//                    }
//                })
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okClient)
                .build();

        apiService = restAdapter.create(InstituteService.class);
    }

    public InstituteService getService()
    {
        return apiService;
    }
}
