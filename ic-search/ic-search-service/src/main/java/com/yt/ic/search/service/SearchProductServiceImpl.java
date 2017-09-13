package com.yt.ic.search.service;


import com.yt.ic.search.api.SearchProductService;
import com.yt.ic.search.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 */
// TODO
@Service
public class SearchProductServiceImpl implements SearchProductService {

    @Autowired
    private SearchCore searchCore;

//    @Autowired
//    private SearchLogDao searchLogDao;

    @Override
    public List<IndexDto> searchCarSeries(String queryString, int n) throws IOException {
//        List<Index> indexes = searchCore.getIndexes(CarIndex.SERIES_SCOPE, queryString,
//                CarIndex.CONTENT, SearchMode.ANALYZED_AND_ALL_IN, n);
//        if(indexes == null || indexes.isEmpty()) {
//            return new ArrayList<>();
//        } else {
//            return indexes.stream().sorted((i1, i2) -> i2.getScore().compareTo(i1.getScore()))
//                    .map(i -> toIndexDto(IndexType.CAR_SERIES, i)).collect(Collectors.toList());
//        }
        return null;
    }

    @Override
    public List<IndexDto> searchCarProduct(String queryString, int n) throws IOException {
//        List<Index> indexes = searchCore.getIndexes(CarIndex.SPU_SCOPE, queryString,
//                CarIndex.CONTENT, SearchMode.ANALYZED_AND_ALL_IN, n);
//        if(indexes == null || indexes.isEmpty()) {
//            return new ArrayList<>();
//        } else {
//            return indexes.stream().sorted((i1, i2) -> i2.getScore().compareTo(i1.getScore()))
//                    .map(i -> toIndexDto(IndexType.CAR_SPU, i)).collect(Collectors.toList());
//        }
        return null;
    }

    @Transactional
    @Override
    public void submitSearchIndex(String clientId, String userId, IndexDto indexDto) {

        if(clientId == null || indexDto == null) {
            throw  new IllegalArgumentException();
        }

//        SearchLogEntity searchLogEntity = new SearchLogEntity();
//        searchLogEntity.setClientId(clientId);
//        searchLogEntity.setUserId(userId);
//        searchLogEntity.setIndexType(indexDto.getType().toString());
//        searchLogEntity.setIndexId(indexDto.getId());
//        searchLogEntity.setIndexName(indexDto.getContent());
//
//        searchLogDao.save(searchLogEntity);
    }

    @Override
    public List<IndexDto> getSearchHistory(String clientId, int maxSize) {
//        if(StringUtils.isNullOrEmpty(clientId)) {
//            throw new IllegalArgumentException();
//        } else {
//            DateTime now = DateTime.now();
//            Date begin = now.minusDays(30).toDate();
//
//            Pageable pageable = JpaPageHelp.convert(new PageRequestCustom(1, 100));
//            Page<SearchLogEntity> logs = searchLogDao
//                    .findByClientIdAndCreatedDateGreaterThanOrderByIdDesc(clientId, begin, pageable);
//
//            Map<String, SearchLogEntity> map = logs.getContent().stream().collect(Collectors.toMap(log -> {
//                StringBuffer buffer = new StringBuffer();
//                buffer.append(log.getClientId());
//                buffer.append(log.getIndexType());
//                buffer.append(log.getIndexId());
//                buffer.append(log.getIndexName());
//                return buffer.toString();
//            }, log -> log, (l1, l2) -> l1.getCreatedDate().before(l2.getCreatedDate()) ? l2 : l1));
//
//            List<SearchLogEntity> filteredLogs = map.values().stream()
//                    .sorted((l1, l2) -> l2.getCreatedDate().compareTo(l1.getCreatedDate()))
//                    .collect(Collectors.toList());
//            filteredLogs = filteredLogs
//                    .subList(0, maxSize > filteredLogs.size() ? filteredLogs.size() : maxSize);
//
//            return filteredLogs.stream().map(l -> toIndexDto(l)).collect(Collectors.toList());
//        }
        return null;
    }

//    private IndexDto toIndexDto(SearchLogEntity entity) {
//        if(entity == null) {
//            return null;
//        }
//        IndexDto index = new IndexDto();
//        index.setType(IndexType.valueOf(entity.getIndexType()));
//        index.setId(entity.getIndexId());
//        index.setContent(entity.getIndexName());
//        return index;
//    }

    private IndexDto toIndexDto(IndexType type, Index index) {
        if(type == null || index == null) {
            return null;
        }
        IndexDto indexDto = null;
        Map<String, String> map = index.getFields().stream().collect(
                Collectors.toMap(IndexField :: getKey, IndexField :: getValue, (f1, f2) -> f1));
        switch (type) {
            case CAR_SERIES:
                indexDto = new IndexDto();
                indexDto.setType(type);
                indexDto.setId(map.get(CarIndex.SERIES_ID));
                indexDto.setContent(map.get(CarIndex.SERIES_NAME));
                indexDto.setExtra(map);
                return indexDto;
            case CAR_SPU:
                indexDto = new IndexDto();
                indexDto.setType(type);
                indexDto.setId(map.get(CarIndex.SPU_ID));
                indexDto.setContent(map.get(CarIndex.SPU_NAME));
                indexDto.setExtra(map);
                return indexDto;
            default:
                return indexDto;
        }
    }
}
