package com.senior.cyber.frmk.common.jwt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Header {

    @Expose
    @SerializedName("alg")
    protected Algorithm algorithm;

    @Expose
    @SerializedName("typ")
    protected String type;

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public String getType() {
        return type;
    }

}