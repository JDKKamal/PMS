package com.jdkgroup.presenter;

import android.content.Context;

import com.jdkgroup.interacter.InterActorCallback;
import com.jdkgroup.models.MainWeather;
import com.jdkgroup.view.WeatherView;
import com.jdkgroup.baseclasses.BasePresenter;
import com.jdkgroup.pms.R;

import java.util.HashMap;

public class WeatherPresenter extends BasePresenter<WeatherView> {

    public void callWeatherApi(final Context context, final HashMap<String, String> params) {

        if (hasInternet()) { //CHECK THE INTERNET CONNECTION

            addSubscription(getAppInteractor().callWeatherApi(params, new InterActorCallback<MainWeather>() {
                @Override
                public void onStart() {
                    getView().showProgressDialog(true);
                }

                @Override
                public void onResponse(MainWeather response) {
                    getView().onSuccess(response);
                }

                @Override
                public void onFinish() {
                    getView().showProgressDialog(false);
                }

                @Override
                public void onError(String message) {
                    getView().onFailure(message);
                }
            }));
        } else {
            getView().onFailure(context.getString(R.string.no_internet_message));
        }
    }
}
