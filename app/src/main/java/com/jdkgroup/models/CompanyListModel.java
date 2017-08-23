package com.jdkgroup.models;

import org.parceler.Parcel;

/**
 * Created by kamlesh on 8/15/2017.
 */

@Parcel
public class CompanyListModel
{
    public long id;
    public String name;
    public boolean isSelect;

    public CompanyListModel() {

    }

    public CompanyListModel(long id, String name, boolean isSelect) {
        this.id = id;
        this.name = name;
        this.isSelect = isSelect;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
