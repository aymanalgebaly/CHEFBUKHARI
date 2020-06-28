package com.compubase.chefbukhari.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class CartModel extends RealmObject {

    private int no ;

    private String category,datee,Des,img1,img2,img3,isfave,price,
            totalPriceItem,title,idProduct,item_number,priceDiscount,extra_request;
    private int id;
    private double item_price;

    public CartModel() {
    }

    public CartModel(int no, String category, String datee, String des, String img1, String img2, String img3, String isfave, String price, String totalPriceItem, String title,
                     String idProduct, String item_number, String priceDiscount, String extra_request, int id, double item_price) {
        this.no = no;
        this.category = category;
        this.datee = datee;
        Des = des;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.isfave = isfave;
        this.price = price;
        this.totalPriceItem = totalPriceItem;
        this.title = title;
        this.idProduct = idProduct;
        this.item_number = item_number;
        this.priceDiscount = priceDiscount;
        this.extra_request = extra_request;
        this.id = id;
        this.item_price = item_price;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDatee() {
        return datee;
    }

    public void setDatee(String datee) {
        this.datee = datee;
    }

    public String getDes() {
        return Des;
    }

    public void setDes(String des) {
        Des = des;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getIsfave() {
        return isfave;
    }

    public void setIsfave(String isfave) {
        this.isfave = isfave;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalPriceItem() {
        return totalPriceItem;
    }

    public void setTotalPriceItem(String totalPriceItem) {
        this.totalPriceItem = totalPriceItem;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getItem_number() {
        return item_number;
    }

    public void setItem_number(String item_number) {
        this.item_number = item_number;
    }

    public String getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(String priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    public String getExtra_request() {
        return extra_request;
    }

    public void setExtra_request(String extra_request) {
        this.extra_request = extra_request;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getItem_price() {
        return item_price;
    }

    public void setItem_price(double item_price) {
        this.item_price = item_price;
    }
}
