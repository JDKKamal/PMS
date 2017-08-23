package com.jdkgroup.pms.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.jdkgroup.baseclasses.SimpleMVPActivity;
import com.jdkgroup.connection.RestConstant;
import com.jdkgroup.models.MainWeather;
import com.jdkgroup.pms.R;
import com.jdkgroup.presenter.WeatherPresenter;
import com.jdkgroup.pms.utils.AppUtils;
import com.jdkgroup.view.WeatherView;

import java.util.HashMap;

import butterknife.ButterKnife;

public class WeatherActivity extends SimpleMVPActivity<WeatherPresenter, WeatherView> implements WeatherView {

    private AppCompatActivity appCompatActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        init();
        APICall();
    }

    private void init() {
        ButterKnife.bind(this);
        appCompatActivity = this;
    }

    @NonNull
    @Override
    public WeatherPresenter createPresenter() {
        return new WeatherPresenter();
    }

    @NonNull
    @Override
    public WeatherView attachView() {
        return this;
    }

    @Override
    public void onSuccess(MainWeather response) {
        AppUtils.showToast(getActivity(), AppUtils.getToJsonClass(response));
    }

    @Override
    public void onFailure(String message) {
        AppUtils.showToast(this, message);
    }

    private void APICall() {
        HashMap<String, String> mapWhether = getDefaultParameter();
        mapWhether.put(RestConstant.key_appid, RestConstant.value_appid);
        mapWhether.put(RestConstant.key_id, RestConstant.value_id);
        getPresenter().callWeatherApi(this, mapWhether);
    }
}
