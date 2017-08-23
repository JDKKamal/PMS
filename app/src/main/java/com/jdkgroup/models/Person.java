package com.jdkgroup.models;

public class Person {
    String name;
    String add;
    String dsg;
    boolean selected;

    public Person(String name, String add, String dsg) {
        this.name = name;
        this.add = add;
        this.dsg = dsg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getDsg() {
        return dsg;
    }

    public void setDsg(String dsg) {
        this.dsg = dsg;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}