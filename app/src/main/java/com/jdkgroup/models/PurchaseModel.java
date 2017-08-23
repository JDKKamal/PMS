package com.jdkgroup.models;

/**
 * Created by kamlesh on 8/11/2017.
 */

public class PurchaseModel
{
    private String title, image, orderdatetime, purchasedatetime, replacedatetime, replacegetdatetime,
            couriercompant, courierpersonname, couriercellno, courieraddress, courierprofile;

    public PurchaseModel(String title, String image, String orderdatetime, String purchasedatetime, String replacedatetime, String replacegetdatetime, String couriercompant, String courierpersonname, String couriercellno, String courieraddress, String courierprofile) {
        this.title = title;
        this.image = image;
        this.orderdatetime = orderdatetime;
        this.purchasedatetime = purchasedatetime;
        this.replacedatetime = replacedatetime;
        this.replacegetdatetime = replacegetdatetime;
        this.couriercompant = couriercompant;
        this.courierpersonname = courierpersonname;
        this.couriercellno = couriercellno;
        this.courieraddress = courieraddress;
        this.courierprofile = courierprofile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOrderdatetime() {
        return orderdatetime;
    }

    public void setOrderdatetime(String orderdatetime) {
        this.orderdatetime = orderdatetime;
    }

    public String getPurchasedatetime() {
        return purchasedatetime;
    }

    public void setPurchasedatetime(String purchasedatetime) {
        this.purchasedatetime = purchasedatetime;
    }

    public String getReplacedatetime() {
        return replacedatetime;
    }

    public void setReplacedatetime(String replacedatetime) {
        this.replacedatetime = replacedatetime;
    }

    public String getReplacegetdatetime() {
        return replacegetdatetime;
    }

    public void setReplacegetdatetime(String replacegetdatetime) {
        this.replacegetdatetime = replacegetdatetime;
    }

    public String getCouriercompant() {
        return couriercompant;
    }

    public void setCouriercompant(String couriercompant) {
        this.couriercompant = couriercompant;
    }

    public String getCourierpersonname() {
        return courierpersonname;
    }

    public void setCourierpersonname(String courierpersonname) {
        this.courierpersonname = courierpersonname;
    }

    public String getCouriercellno() {
        return couriercellno;
    }

    public void setCouriercellno(String couriercellno) {
        this.couriercellno = couriercellno;
    }

    public String getCourieraddress() {
        return courieraddress;
    }

    public void setCourieraddress(String courieraddress) {
        this.courieraddress = courieraddress;
    }

    public String getCourierprofile() {
        return courierprofile;
    }

    public void setCourierprofile(String courierprofile) {
        this.courierprofile = courierprofile;
    }
}
