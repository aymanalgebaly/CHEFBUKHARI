
package com.compubase.chefbukhari.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrdersResponse implements Parcelable {

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
    @SerializedName("lan")
    @Expose
    private String lan;
    @SerializedName("lon")
    @Expose
    private String lon;
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

    public OrdersResponse() {
    }

    protected OrdersResponse(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            idUser = null;
        } else {
            idUser = in.readInt();
        }
        if (in.readByte() == 0) {
            idProduct = null;
        } else {
            idProduct = in.readInt();
        }
        address = in.readString();
        totlePrice = in.readString();
        datee = in.readString();
        idAdmin = in.readString();
    }

    public static final Creator<OrdersResponse> CREATOR = new Creator<OrdersResponse>() {
        @Override
        public OrdersResponse createFromParcel(Parcel in) {
            return new OrdersResponse(in);
        }

        @Override
        public OrdersResponse[] newArray(int size) {
            return new OrdersResponse[size];
        }
    };

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

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        if (idUser == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(idUser);
        }
        if (idProduct == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(idProduct);
        }
        parcel.writeString(address);
        parcel.writeString(totlePrice);
        parcel.writeString(datee);
        parcel.writeString(idAdmin);
    }
}
