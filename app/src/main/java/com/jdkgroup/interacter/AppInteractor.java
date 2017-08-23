package com.jdkgroup.interacter;

import com.jdkgroup.connection.RestClient;
import com.jdkgroup.connection.RestConstant;
import com.jdkgroup.models.MainWeather;
import com.jdkgroup.models.Response;
import com.jdkgroup.pms.utils.AppUtils;

import java.util.HashMap;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.jdkgroup.connection.RestClient.getPrimaryService;

public class AppInteractor {

    public boolean isCancel;

    public AppInteractor() {
    }

    private void sendResponse(InterActorCallback callback, Response response) {
        if (!isCancel) {
            callback.onResponse(response);
        }
    }

    public boolean isCancel() {
        return isCancel;
    }

    public void cancel() {
        isCancel = true;
    }

    public Subscription callWeatherApi(final HashMap<String, String> params, final InterActorCallback<MainWeather> callback) {
        return getPrimaryService(RestClient.TIME).apiGet(RestConstant.BASE_URL + RestConstant.WEATHER, params)
                .map(s -> {
                    AppUtils.displayMap(params);
                    return AppUtils.getFromJson(s, MainWeather.class);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new InterActorOnSubscribe<>(callback))
                .subscribe(new InterActorSubscriber<MainWeather>(callback, this) {
                    @Override
                    public void onNext(MainWeather response) {
                        sendResponse(callback, response);
                    }
                });
    }

}

