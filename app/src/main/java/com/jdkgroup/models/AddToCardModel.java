package com.jdkgroup.models;

/**
 * Created by kamlesh on 8/11/2017.
 */

public class AddToCardModel {
    private String id, companyname, title;
    private int discount, quantity, isaddtocart;
    private double price, discountprice, totalprice;
    private boolean isSelected;

    public AddToCardModel(String id, String companyname, String title, int discount, int quantity, int isaddtocart, double price, double discountprice, double totalprice, boolean isSelected) {
        this.id = id;
        this.companyname = companyname;
        this.title = title;
        this.discount = discount;
        this.quantity = quantity;
        this.isaddtocart = isaddtocart;
        this.price = price;
        this.discountprice = discountprice;
        this.totalprice = totalprice;
        this.isSelected = isSelected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIsaddtocart() {
        return isaddtocart;
    }

    public void setIsaddtocart(int isaddtocart) {
        this.isaddtocart = isaddtocart;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscountprice() {
        return discountprice;
    }

    public void setDiscountprice(double discountprice) {
        this.discountprice = discountprice;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}