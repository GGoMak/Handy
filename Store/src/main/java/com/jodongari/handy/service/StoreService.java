package com.jodongari.handy.service;

import com.jodongari.handy.protocol.requestDto.ManageTableInfoRequestDto;
import com.jodongari.handy.protocol.requestDto.RegisterStoreRequestDto;
import com.jodongari.handy.protocol.responseDto.ManageTableInfoResponseDto;
import com.jodongari.handy.protocol.responseDto.RegisterStoreResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface StoreService {

    ManageTableInfoResponseDto manageTableInfo(ManageTableInfoRequestDto request);

    RegisterStoreResponseDto registerStore(RegisterStoreRequestDto request,
                                           MultipartFile storeImage,
                                           MultipartFile businessReportCardImage,
                                           MultipartFile businessLicenseImage,
                                           MultipartFile logoImage) throws Exception;
}
