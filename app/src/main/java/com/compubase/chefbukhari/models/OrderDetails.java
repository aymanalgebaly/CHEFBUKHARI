
package com.compubase.chefbukhari.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetails {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_user")
    @Expose
    private Integer idUser;
    @SerializedName("id_product")
    @Expose
    private Integer idProduct;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("totle_price")
    @Expose
    private String totlePrice;
    @SerializedName("datee")
    @Expose
    private String datee;
    @SerializedName("timee")
    @Expose
    private String timee;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("sitelan")
    @Expose
    private String sitelan;
    @SerializedName("sitelon")
    @Expose
    private String sitelon;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("id_admin")
    @Expose
    private String idAdmin;
    @SerializedName("id_agent")
    @Expose
    private String idAgent;
    @SerializedName("deliver_details")
    @Expose
    private String deliverDetails;
    @SerializedName("deliver_code")
    @Expose
    private String deliverCode;
    @SerializedName("copon_code")
    @Expose
    private String coponCode;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("branch")
    @Expose
    private String branch;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotlePrice() {
        return totlePrice;
    }

    public void setTotlePrice(String totlePrice) {
        this.totlePrice = totlePrice;
    }

    public String getDatee() {
        return datee;
    }

    public void setDatee(String datee) {
        this.datee = datee;
    }

    public String getTimee() {
        return timee;
    }

    public void setTimee(String timee) {
        this.timee = timee;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSitelan() {
        return sitelan;
    }

    public void setSitelan(String sitelan) {
        this.sitelan = sitelan;
    }

    public String getSitelon() {
        return sitelon;
    }

    public void setSitelon(String sitelon) {
        this.sitelon = sitelon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getIdAgent() {
        return idAgent;
    }

    public void setIdAgent(String idAgent) {
        this.idAgent = idAgent;
    }

    public String getDeliverDetails() {
        return deliverDetails;
    }

    public void setDeliverDetails(String deliverDetails) {
        this.deliverDetails = deliverDetails;
    }

    public String getDeliverCode() {
        return deliverCode;
    }

    public void setDeliverCode(String deliverCode) {
        this.deliverCode = deliverCode;
    }

    public String getCoponCode() {
        return coponCode;
    }

    public void setCoponCode(String coponCode) {
        this.coponCode = coponCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

}
