package com.jdkgroup.interacter;

import com.jdkgroup.models.Response;

import rx.functions.Action0;

class InterActorOnSubscribe<T extends Response> implements Action0 {

  private InterActorCallback<T> mInterActorCallback;

  InterActorOnSubscribe(InterActorCallback<T> mInterActorCallback) {
    this.mInterActorCallback = mInterActorCallback;
  }

  @Override
  public void call() {
    mInterActorCallback.onStart();
  }
}
