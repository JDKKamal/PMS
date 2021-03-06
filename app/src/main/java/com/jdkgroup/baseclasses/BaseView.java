package com.jdkgroup.baseclasses;

import android.app.Activity;
import android.view.View;
import java.util.Map;

public interface BaseView<T> {
    boolean hasInternet();
    boolean hasInternetWithoutMessage();
    void showProgressDialog(boolean show);
    void showProgressToolBar(boolean show, View view);
    void onSuccess(T response);
    void onFailure(String message);
    void onAuthenticationFailure(String message);
    Map<String, String> getDefaultParameter();
    Activity getActivity();
}