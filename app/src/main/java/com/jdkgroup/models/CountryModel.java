package com.jdkgroup.models;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * Created by samset on 27/05/16.
 */
@RealmClass
public class CountryModel extends RealmObject {

    private String id;
    @Required
    private String name;
    @Required
    private String code;

    public CountryModel() {
    }

    public CountryModel(String id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
