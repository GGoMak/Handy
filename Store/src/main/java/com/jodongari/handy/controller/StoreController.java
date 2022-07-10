package com.jodongari.handy.controller;

import com.jodongari.handy.protocol.ApiMessage;
import com.jodongari.handy.protocol.requestDto.DeleteQRCodeRequestDto;
import com.jodongari.handy.protocol.requestDto.RegisterStoreRequestDto;
import com.jodongari.handy.protocol.requestDto.RegisterQRCodeRequestDto;
import com.jodongari.handy.protocol.responseDto.DeleteQRCodeResponseDto;
import com.jodongari.handy.protocol.responseDto.RegisterStoreResponseDto;
import com.jodongari.handy.protocol.responseDto.RegisterQRCodeResponseDto;
import com.jodongari.handy.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/register")
    public ApiMessage<RegisterStoreResponseDto> registerStore(RegisterStoreRequestDto request) {
        final RegisterStoreResponseDto response = storeService.registerStore(request);

        return ApiMessage.success(response);
    }

    @PostMapping("/qrcode/register")
    public ApiMessage<RegisterQRCodeResponseDto> registerQRCode(RegisterQRCodeRequestDto request) {
        return ApiMessage.success(storeService.registerQRCode(request));
    }

    @DeleteMapping("/qrcode/delete")
    public ApiMessage<DeleteQRCodeResponseDto> deleteQRCode(DeleteQRCodeRequestDto request) {
        return ApiMessage.success(storeService.deleteQRCode(request));
    }
}
