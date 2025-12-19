package ru.mirea.gracheva.data.storage.network;

import com.google.gson.annotations.SerializedName;

public class MetalPriceResponse {
    @SerializedName("Date") public String date;
    @SerializedName("MetalID") public int metalNumCode;  // 0=Золото, 1=Серебро, 2=Платина
    @SerializedName("CertificateRubles") public double officialRate;  // ✅ Реальная цена!
}
