package com.jodongari.handy.service.impl;

import com.google.common.hash.Hashing;
import com.jodongari.handy.domain.entity.QREntity;
import com.jodongari.handy.protocol.requestDto.ManageTableInfoRequestDto;
import com.jodongari.handy.protocol.requestDto.RegisterStoreRequestDto;
import com.jodongari.handy.protocol.responseDto.ManageTableInfoResponseDto;
import com.jodongari.handy.protocol.responseDto.RegisterStoreResponseDto;
import com.jodongari.handy.repository.QRCodeRepository;
import com.jodongari.handy.repository.StoreRepository;
import com.jodongari.handy.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final QRCodeRepository qrCodeRepository;

    @Transactional
    public ManageTableInfoResponseDto manageTableInfo(ManageTableInfoRequestDto request) {
        final Long storeSeq = request.getStoreSeq();
        final Map<String, String> tableInfos = request.getTableInfos();

        final List<QREntity> tableInfoFromDB = qrCodeRepository.findAllByStoreSeq(storeSeq);
        final Map<String, String> addTableMap = makeAddTableMap(tableInfoFromDB, tableInfos);
        final Map<String, String> deleteTableMap = makeDeleteTableMap(tableInfoFromDB, tableInfos);
        final Map<String, String> updateTableMap = makeUpdateTableMap(tableInfoFromDB, tableInfos);

        for (Map.Entry<String,String> entry : addTableMap.entrySet()) {
            String qrHash = entry.getKey();
            String tableName = entry.getValue();

            qrCodeRepository.save(QREntity.builder()
                            .hash(qrHash)
                            .storeSeq(storeSeq)
                            .tableName(tableName)
                            .build());
        }

        for (Map.Entry<String,String> entry : deleteTableMap.entrySet()) {
            String qrHash = entry.getKey();
            qrCodeRepository.deleteById(qrHash);
        }

        for(QREntity entity : tableInfoFromDB) {
            if (updateTableMap.containsKey(entity.getHash())) {
                entity.updateTableName(updateTableMap.get(entity.getHash()));
            }
        }

        return new ManageTableInfoResponseDto();
    }


    @Transactional
    public RegisterStoreResponseDto registerStore(RegisterStoreRequestDto request) {
        return new RegisterStoreResponseDto("");
    }

    private static String generateQRCode() {
        final String qrHash = Hashing.sha256()
                .hashString(new Date().toString(), StandardCharsets.UTF_8)
                .toString();
        return qrHash;
    }

    private static Map<String, String> makeAddTableMap(List<QREntity> tableInfoFromDB,
                                                       Map<String, String> tableInfos){

        final Map<String, String> db = qrEntityListToMap(tableInfoFromDB);
        final Map<String, String> addTableInfoMap = new HashMap<>();

        for(Map.Entry<String,String> entry : tableInfos.entrySet()) {
            String hash = entry.getKey();
            String tableName = entry.getValue();

            if(!db.containsKey(hash)){
                addTableInfoMap.put(generateQRCode(), tableName);
            }
        }

        return addTableInfoMap;
    }

    private static Map<String, String> makeUpdateTableMap(List<QREntity> tableInfoFromDB,
                                                          Map<String, String> tableInfos) {
        final Map<String, String> db = qrEntityListToMap(tableInfoFromDB);
        final Map<String, String> updateTableInfoMap = new HashMap<>();

        for(Map.Entry<String,String> entry : tableInfos.entrySet()) {
            String hash = entry.getKey();
            String tableName = entry.getValue();

            if(db.containsKey(hash)){
                updateTableInfoMap.put(hash, tableName);
            }
        }

        return updateTableInfoMap;
    }

    private static Map<String, String> makeDeleteTableMap(List<QREntity> tableInfoFromDB,
                                                          Map<String, String> tableInfos){

        final Map<String, String> db = qrEntityListToMap(tableInfoFromDB);
        final Map<String, String> deleteTableInfoMap = new HashMap<>();

        for(Map.Entry<String, String> entry : db.entrySet()){
            String hash = entry.getKey();
            String tableName = entry.getValue();

            if(!tableInfos.containsKey(hash)){
                deleteTableInfoMap.put(hash, tableName);
            }
        }

        return deleteTableInfoMap;
    }

    private static Map<String, String> qrEntityListToMap(List<QREntity> entities) {
        return entities.stream()
                .collect(Collectors.toMap(QREntity::getHash, QREntity::getTableName));
    }
}