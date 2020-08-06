
package com.compubase.chefbukhari.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class ProductsModel extends RealmObject implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("des")
    @Expose
    private String des;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("title_en")
    @Expose
    private String titleEn;
    @SerializedName("des_en")
    @Expose
    private String desEn;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("img_1")
    @Expose
    private String img1;
    @SerializedName("img_2")
    @Expose
    private String img2;
    @SerializedName("img_3")
    @Expose
    private String img3;
    @SerializedName("datee")
    @Expose
    private String datee;
    @SerializedName("isfav")
    @Expose
    private String isfav;
    @SerializedName("price_discount")
    @Expose
    private String priceDiscount;

    public ProductsModel() {
    }

    protected ProductsModel(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        title = in.readString();
        des = in.readString();
        price = in.readString();
        titleEn = in.readString();
        desEn = in.readString();
        category = in.readString();
        rate = in.readString();
        img1 = in.readString();
        img2 = in.readString();
        img3 = in.readString();
        datee = in.readString();
        isfav = in.readString();
        priceDiscount = in.readString();
    }

    public static final Creator<ProductsModel> CREATOR = new Creator<ProductsModel>() {
        @Override
        public ProductsModel createFromParcel(Parcel in) {
            return new ProductsModel(in);
        }

        @Override
        public ProductsModel[] newArray(int size) {
            return new ProductsModel[size];
        }
    };

    public String getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(String priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getDesEn() {
        return desEn;
    }

    public void setDesEn(String desEn) {
        this.desEn = desEn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
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

    public String getDatee() {
        return datee;
    }

    public void setDatee(String datee) {
        this.datee = datee;
    }

    public String getIsfav() {
        return isfav;
    }

    public void setIsfav(String isfav) {
        this.isfav = isfav;
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
        parcel.writeString(title);
        parcel.writeString(des);
        parcel.writeString(price);
        parcel.writeString(titleEn);
        parcel.writeString(desEn);
        parcel.writeString(category);
        parcel.writeString(rate);
        parcel.writeString(img1);
        parcel.writeString(img2);
        parcel.writeString(img3);
        parcel.writeString(datee);
        parcel.writeString(isfav);
        parcel.writeString(priceDiscount);
    }
}
