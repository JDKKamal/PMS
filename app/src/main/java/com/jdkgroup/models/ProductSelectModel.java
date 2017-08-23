package com.jdkgroup.models;

import org.parceler.Parcel;

/**
 * Created by kamlesh on 8/15/2017.
 */

@Parcel
public class ProductSelectModel
{
    public int id;
    public String name;
    public boolean isSelect;

    public ProductSelectModel() {

    }

    public ProductSelectModel(int id, String name, boolean isSelect) {
        this.id = id;
        this.name = name;
        this.isSelect = isSelect;
    }

    public ProductSelectModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
