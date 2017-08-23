package com.jdkgroup.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SyncData<T> {

    @SerializedName("inserted")
    private List<T> inserted;
    @SerializedName("updated")
    private List<T> updated;

    public List<T> getUpdated() {
        return updated;
    }

    public void setUpdated(List<T> updated) {
        this.updated = updated;
    }

    public List<T> getInserted() {
        return inserted;
    }

    public void setInserted(List<T> inserted) {
        this.inserted = inserted;
    }

    public T getT(int i){
        return inserted.get(i);
    }
}
