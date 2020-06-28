
package com.compubase.chefbukhari.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrdersAgentResponse implements Parcelable {

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

    public OrdersAgentResponse() {
    }

    protected OrdersAgentResponse(Parcel in) {
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
        timee = in.readString();
        area = in.readString();
        sitelan = in.readString();
        sitelon = in.readString();
        status = in.readString();
        idAdmin = in.readString();
        idAgent = in.readString();
        deliverDetails = in.readString();
        deliverCode = in.readString();
        coponCode = in.readString();
        city = in.readString();
        branch = in.readString();
    }

    public static final Creator<OrdersAgentResponse> CREATOR = new Creator<OrdersAgentResponse>() {
        @Override
        public OrdersAgentResponse createFromParcel(Parcel in) {
            return new OrdersAgentResponse(in);
        }

        @Override
        public OrdersAgentResponse[] newArray(int size) {
            return new OrdersAgentResponse[size];
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
        parcel.writeString(timee);
        parcel.writeString(area);
        parcel.writeString(sitelan);
        parcel.writeString(sitelon);
        parcel.writeString(status);
        parcel.writeString(idAdmin);
        parcel.writeString(idAgent);
        parcel.writeString(deliverDetails);
        parcel.writeString(deliverCode);
        parcel.writeString(coponCode);
        parcel.writeString(city);
        parcel.writeString(branch);
    }
}
