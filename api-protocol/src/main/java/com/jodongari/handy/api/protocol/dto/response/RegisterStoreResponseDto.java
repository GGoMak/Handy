package com.jodongari.handy.api.protocol.dto.response;

import lombok.Data;

@Data
public class RegisterStoreResponseDto {
    Long seq;
    Long ownerSeq;
    String name;
    String businessReportCardImageUrl;
    String businessLicenseImageUrl;
    String businessName;
    String businessPersonName;
    String businessNumber;
    String businessAddress;
    String address;
    String telNumber;
    String introduction;
    String openTime;
    String dayOff;
    String originCountry;
    String logoImageUrl;
    String backgroundImageUrl;
    Integer tableCount;
    String category;
}
