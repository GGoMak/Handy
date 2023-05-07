package com.jodongari.handy.controller;

import com.jodongari.handy.api.protocol.dto.request.ManageTableInfoRequestDto;
import com.jodongari.handy.api.protocol.dto.response.GetTableInfoResponseDto;
import com.jodongari.handy.protocol.api.ErrorResponse;
import com.jodongari.handy.protocol.url.TableInfoApiUrl;
import com.jodongari.handy.service.TableInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TableInfoController {

    private final TableInfoService tableInfoService;

    @Operation(summary = "테이블 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(TableInfoApiUrl.TABLE_INFO_GET + "/{storeSeq}")
    public List<GetTableInfoResponseDto> getTable(@PathVariable Long storeSeq) {
        return tableInfoService.getTableInfos(storeSeq);
    }

    @Operation(summary = "테이블 정보 추가, 수정, 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ManageTableInfoRequestDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(TableInfoApiUrl.TABLE_INFO_MANAGE)
    public void manageTableInfo(@RequestBody List<ManageTableInfoRequestDto> requests) {
        tableInfoService.manageTableInfo(requests);
    }
}
