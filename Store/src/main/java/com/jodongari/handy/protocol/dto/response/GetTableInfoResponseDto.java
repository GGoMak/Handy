package com.jodongari.handy.protocol.dto.response;

import com.jodongari.handy.domain.tableInfo.TableInfo;
import lombok.Data;

@Data
public class GetTableInfoResponseDto {
    Long tableSeq;
    Long storeSeq;
    String tableName;
    String tableHash;
    TableInfo.TableInfoStatus status;
}
