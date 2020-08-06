
package com.compubase.chefbukhari.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductsSizeModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("id_product")
    @Expose
    private String idProduct;
    @SerializedName("datee")
    @Expose
    private String datee;
    @SerializedName("size_en")
    @Expose
    private String sizeEn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getDatee() {
        return datee;
    }

    public void setDatee(String datee) {
        this.datee = datee;
    }

    public String getSizeEn() {
        return sizeEn;
    }

    public void setSizeEn(String sizeEn) {
        this.sizeEn = sizeEn;
    }

}
