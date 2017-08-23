package com.jdkgroup.baseclasses;

import com.jdkgroup.interacter.AppInteractor;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<V extends BaseView> {
    protected AppInteractor appInteractor;
    private V view;
    private CompositeSubscription subscription = new CompositeSubscription();

    final void attachView(V view) {
        this.view = view;
    }

    final void detachView() {
        this.view = null;
        if (subscription != null) {
            subscription.clear();
        }
    }

    public V getView() {
        return view;
    }

    public boolean hasInternet() {
        return view.hasInternet();
    }

    public boolean hasInternetWithoutMessage(){
        return view.hasInternetWithoutMessage();
    }
    public boolean isViewAttached() {
        return view != null;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before requesting data to the Presenter");
        }
    }

    protected void addSubscription(Subscription s) {
        subscription.add(s);
    }

    //TODO ADDING SUBSCRIPTION WITH KEY
    protected void addSubscription(Subscription subscription, String key, boolean removePrevious) {
        /*if (removePrevious && subscriptionHashMap.containsKey(key)) {
            Subscription foundSubscription = subscriptionHashMap.get(key);
            if (foundSubscription != null && !foundSubscription.isUnsubscribed()) {
                foundSubscription.unsubscribe();
                subscriptionHashMap.remove(key);
            }
        }
        subscriptionHashMap.put(key, subscription);*/
    }

    protected final AppInteractor getAppInteractor() {
        if (appInteractor == null) {
            appInteractor = new AppInteractor();
        }
        return appInteractor;
    }
}
