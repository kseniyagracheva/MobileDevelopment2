package ru.mirea.gracheva.data.storage.network.metal;

import com.google.gson.annotations.SerializedName;

public class MetalPriceResponse {
    @SerializedName("Date") public String date;
    @SerializedName("MetalID") public int metalNumCode;
    @SerializedName("CertificateRubles") public double officialRate;
}
