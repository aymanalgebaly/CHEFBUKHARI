
package com.compubase.chefbukhari.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SliderModel {

    @SerializedName("slider")
    @Expose
    private String slider;

    public String getSlider() {
        return slider;
    }

    public void setSlider(String slider) {
        this.slider = slider;
    }

}
