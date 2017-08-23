package com.jdkgroup.connection;

import android.util.Base64;

import com.jdkgroup.pms.utils.AppUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class RestClient {

    public static final int TIME = 50;
    private static String CREDENTIALS = "admin:admin@123";

    private static RestService restService;
    //public static BaseApplication application;

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE);

    private static OkHttpClient httpClient = new OkHttpClient().newBuilder()
            .connectTimeout(TIME, TimeUnit.SECONDS)
            .readTimeout(TIME, TimeUnit.SECONDS)
            .writeTimeout(TIME, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(getAuthorization(chain, 1)); //BASIC AUTH
                    if (response.isSuccessful()) {
                        String data = response.body().string();
                        //logoutUser(data);
                        return response.newBuilder().body(ResponseBody.create(response.body().contentType(), data)).build();
                    }
                    return response;
                }
            })
            .build();

    private static OkHttpClient httpLogoutClient = new OkHttpClient().newBuilder()
            .connectTimeout(TIME, TimeUnit.SECONDS)
            .readTimeout(TIME, TimeUnit.SECONDS)
            .writeTimeout(TIME, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(getAuthorization(chain, 2));
                    if (response.isSuccessful()) {
                        String data = response.body().string();
                        //logoutUser(data);
                        return response.newBuilder().body(ResponseBody.create(response.body().contentType(), data)).build();
                    }
                    return response;
                }
            })
            .build();

    public static RestService getPrimaryService(int time) {
        OkHttpClient httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(time, TimeUnit.SECONDS)
                .readTimeout(time, TimeUnit.SECONDS)
                .writeTimeout(time, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response response = chain.proceed(getAuthorization(chain, 1));
                        if (response.isSuccessful()) {
                            String data = response.body().string();
                            //logoutUser(data);
                            return response.newBuilder().body(ResponseBody.create(response.body().contentType(), data)).build();
                        }
                        return response;
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestConstant.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(new ToStringConverterFactory())
                .client(httpClient)
                .build();
        return retrofit.create(RestService.class);
    }

    public static RestService getService(int isRestService) {
        if (restService == null) {
           if(isRestService == 1) {
               Retrofit retrofit = new Retrofit.Builder().baseUrl(RestConstant.BASE_URL)
                       .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                       .addConverterFactory(new ToStringConverterFactory())
                       .client(httpLogoutClient) //TODO LOGOUT
                       .build();
               return restService = retrofit.create(RestService.class);
           }
           else if(isRestService == 2)
           {
               Retrofit retrofit = new Retrofit.Builder().baseUrl(RestConstant.BASE_URL)
                       .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                       .addConverterFactory(new ToStringConverterFactory())
                       .client(httpClient) //BASIC AUTH
                       .build();
               return retrofit.create(RestService.class);
           }
        }
        return null;
    }

    private static OkHttpClient okHttpClientInterceptorLogging = new OkHttpClient().newBuilder()
            .connectTimeout(TIME, TimeUnit.SECONDS)
            .readTimeout(TIME, TimeUnit.SECONDS)
            .writeTimeout(TIME, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build();

    private static OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(TIME, TimeUnit.SECONDS)
            .readTimeout(TIME, TimeUnit.SECONDS)
            .writeTimeout(TIME, TimeUnit.SECONDS)
            .build();

    public static Request getAuthorization(Interceptor.Chain chain, int isAuthorization) {
        Request original;
        Request.Builder requestBuilder;
        String basic;
        if (isAuthorization == 1) //TODO BASIC AUTH
        {
            basic = "Basic " + Base64.encodeToString(CREDENTIALS.getBytes(), Base64.NO_WRAP);
            original = chain.request();
            requestBuilder = original.newBuilder().header("Authorization", basic);
            requestBuilder.header("Accept", "application/json");
            requestBuilder.method(original.method(), original.body());
            //requestBuilder.header("Authorization",  RestConstant.AUTHHORIZATION);
            return requestBuilder.build();
        } else if (isAuthorization == 2) //TODO NO AUTH AND BASIC
        {
            original = chain.request();
            requestBuilder = original.newBuilder();
            return requestBuilder.build();
        }
        return null;
    }

    public static void logoutUser(String responseData) {
        AppUtils.loge(RestConstant.API_RESPONSE + responseData);
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            if (jsonObject.has("authentication") && !jsonObject.optBoolean("authentication")) {
               /* String deviceToken= PreferenceUtils.getInstance(BaseApplication.getBaseApplication()).getDeviceToken();
                PreferenceUtils.getInstance(BaseApplication.getBaseApplication()).clearAllPrefs();
                Intent intent = new Intent(BaseApplication.getBaseApplication(), WeatherActivity.class);
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK );
                BaseApplication.getBaseApplication().startActivity(intent);
                PreferenceUtils.getInstance(BaseApplication.getBaseApplication()).setDeviceToken(deviceToken);*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}