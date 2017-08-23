package com.jdkgroup.interacter;

import android.content.ActivityNotFoundException;

import com.jdkgroup.connection.RestConstant;
import com.jdkgroup.models.Response;
import com.jdkgroup.pms.utils.AppUtils;
import com.jdkgroup.pms.R;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import rx.Subscriber;

import static com.jdkgroup.baseclasses.BaseApplication.getBaseApplication;

abstract class InterActorSubscriber<T extends Response> extends Subscriber<T> {

    private InterActorCallback<T> mInterActorCallback;
    private AppInteractor appInteractor;

    InterActorSubscriber(InterActorCallback<T> mInterActorCallback, AppInteractor appInteractor) {
        this.mInterActorCallback = mInterActorCallback;
        this.appInteractor = appInteractor;
    }

    @Override
    public void onCompleted() {
        if (!appInteractor.isCancel) {
            mInterActorCallback.onFinish();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (!appInteractor.isCancel) {
            AppUtils.loge(RestConstant.API_CALL_ERROR + e.toString());
            if (e instanceof SocketTimeoutException) {
                mInterActorCallback.onError(getBaseApplication().getResources().getString(R.string.msg_connection_time_out));
            } else if (e instanceof ActivityNotFoundException) {
                mInterActorCallback.onError(getBaseApplication().getResources().getString(R.string.msg_activity_not_found));
            } else if (e instanceof UnknownHostException || e instanceof ConnectException) {
                mInterActorCallback.onError(getBaseApplication().getResources().getString(R.string.msg_server_not_responding));
            } else {
                mInterActorCallback.onError(getBaseApplication().getResources().getString(R.string.msg_server_not_responding));
            }
            mInterActorCallback.onFinish();
        }
    }
}