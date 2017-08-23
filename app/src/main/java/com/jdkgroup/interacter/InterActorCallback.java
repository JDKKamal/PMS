package com.jdkgroup.interacter;

import com.jdkgroup.models.Response;

public interface InterActorCallback<T extends Response> {

  public void onStart();

  public void onResponse(T response);

  public void onFinish();

  public void onError(String message);

}
