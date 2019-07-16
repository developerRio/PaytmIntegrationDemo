package com.originalstocks.paytmintegrationdemo.Model;

import com.google.gson.annotations.SerializedName;

public class PaytmModel {

    private String mId;
    private String orderId;
    private String custId;
    private String channelId;
    private String txtAmount;
    private String website;
    private String callbackURL;
    private String industryTypeId;
    private String CheckSumHash;

    public PaytmModel(String mId, String orderId, String custId, String channelId, String txtAmount, String website, String callbackURL, String industryTypeId, String checkSumHash) {
        this.mId = mId;
        this.orderId = orderId;
        this.custId = custId;
        this.channelId = channelId;
        this.txtAmount = txtAmount;
        this.website = website;
        this.callbackURL = callbackURL;
        this.industryTypeId = industryTypeId;
        CheckSumHash = checkSumHash;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTxtAmount() {
        return txtAmount;
    }

    public void setTxtAmount(String txtAmount) {
        this.txtAmount = txtAmount;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCallbackURL() {
        return callbackURL;
    }

    public void setCallbackURL(String callbackURL) {
        this.callbackURL = callbackURL;
    }

    public String getIndustryTypeId() {
        return industryTypeId;
    }

    public void setIndustryTypeId(String industryTypeId) {
        this.industryTypeId = industryTypeId;
    }

    public String getCheckSumHash() {
        return CheckSumHash;
    }

    public void setCheckSumHash(String checkSumHash) {
        CheckSumHash = checkSumHash;
    }
}
