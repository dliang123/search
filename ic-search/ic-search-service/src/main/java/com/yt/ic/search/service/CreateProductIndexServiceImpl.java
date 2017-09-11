package com.yt.ic.search.service;


import com.google.common.collect.Lists;
import com.yt.demo.core.dao.BrandDao;
import com.yt.demo.core.dao.ModelsDao;
import com.yt.demo.core.entity.BrandEntity;
import com.yt.demo.core.entity.ModelEntity;
import com.yt.ic.search.api.CreateProductIndexService;
import com.yt.ic.search.model.CarIndex;
import com.yt.ic.search.model.Index;
import com.yt.ic.search.model.IndexField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 该服务不通过RPC提供，用于job建立索引
 * Created by icewang on 2017-03-10.
 */
@Service
public class CreateProductIndexServiceImpl implements CreateProductIndexService {

    private static final Logger logger =
            LoggerFactory.getLogger(CreateProductIndexService.class);


    @Autowired
    private SearchCore searchCore;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private ModelsDao modelsDao;

    @Override
    public void createCarSeriesIndex() {
        List<BrandEntity> brands =
                brandDao.findAll();
        List<Long> ids = brands.stream().map(BrandEntity::getId)
                .collect(Collectors.toList());
        Map<Long, String> brandMap = brands.stream()
                .collect(Collectors.toMap(BrandEntity::getId, BrandEntity::getZhName, (b1, b2) -> b1));

        int i = 0;
        int j = i + 50;
        while (i < ids.size()) {
            List<Long> theIds = ids.subList(i, j > ids.size() ? ids.size() : j);

            List<ModelEntity> models = modelsDao.findByBrandIdInAndIsDelete(theIds, false);
            try {
                // 分页查询车系
                // 建立索引
                logger.info("品牌ids:" + theIds.toString());
                List<Index> indexes = models.stream()
                        .map(model -> modelToIndex(model, brandMap)).collect(Collectors.toList());
                searchCore.createIndexes(CarIndex.SERIES_SCOPE, indexes);
                Thread.sleep(200); // 防止CPU过高
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            } finally {
                i = j;
                j += 50;
            }
        }
    }

    private Index modelToIndex(ModelEntity model, Map<Long, String> brandMap) {
        List<IndexField> fields = Lists.newArrayList();
        fields.add(new IndexField(CarIndex.BRAND_ID, String.valueOf(model.getBrandId()), false, false));
        fields.add(new IndexField(CarIndex.SERIES_ID, String.valueOf(model.getId()), false, false));
        fields.add(new IndexField(CarIndex.SERIES_NAME, model.getZhName(), false, false));

        String brandName = brandMap.get(model.getBrandId());
        String content = null != brandName
                ? brandName + " " + model.getZhName() : model.getZhName();
        fields.add(new IndexField(CarIndex.CONTENT, content, false, true));

        Index index = new Index();
        index.setFields(fields);
        return index;
    }

    @Override
    public void createCarProductIndex() {
        boolean isLastPage = false;
        int pageIndex = 1;
        do {
//            SPUPageRequest request = new SPUPageRequest();
//            request.setCategoryCode("0");
//            request.setDeleted(false);
//            request.setPageIndex(pageIndex);
//            request.setPageSize(200);
//
//            PageCustom<SPU> pageResult;
//            try {
//                // 分页查询SPU
//                pageResult = spuService.pagesProduct(request);
//                isLastPage = pageResult.isLast();
//
//                // 建立索引
//                List<Index> indexes = pageResult.getContent().stream()
//                        .map(spu -> spuToIndex(spu)).collect(Collectors.toList());
//                searchCore.createIndexes(CarIndex.SPU_SCOPE, indexes);
//
//                Thread.sleep(200); // 防止CPU过高
//            } catch (Exception e) {
//                logger.error(e.getMessage(), e);
//            } finally {
//                pageIndex++;
//            }
        } while (!isLastPage);
    }

    @Override
    public void createCarItemIndex() {
        // TODO
        throw new NotImplementedException();
    }

    @Override
    public void deleteCarSeriesIndex() {
        try {
            searchCore.deleteAllIndexes(CarIndex.SERIES_SCOPE);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteCarProductIndex() {
        try {
            searchCore.deleteAllIndexes(CarIndex.SPU_SCOPE);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

//    private Index modelToIndex(ModelsResponse model, Map<Long, String> brandMap) {
//        List<IndexField> fields = new ArrayList<>();
//        fields.add(new IndexField(CarIndex.BRAND_ID, String.valueOf(model.getBrandId()), false, false));
//        fields.add(new IndexField(CarIndex.SERIES_ID, String.valueOf(model.getId()), false, false));
//        fields.add(new IndexField(CarIndex.SERIES_NAME, model.getZhName(), false, false));
//
//        String brandName = brandMap.get(model.getBrandId());
//        String content = StringUtils.isNotBlank(brandName)
//                ? brandName + " " + model.getZhName() : model.getZhName();
//        fields.add(new IndexField(CarIndex.CONTENT, content, false, true));
//
//        Index index = new Index();
//        index.setFields(fields);
//        return index;
//    }

//    private Index spuToIndex(SPU spu) {
//        List<IndexField> fields =  new ArrayList<>();
//        fields.add(new IndexField(CarIndex.SPU_ID, String.valueOf(spu.getId()), false, false));
//        fields.add(new IndexField(CarIndex.SPU_NAME, spu.getName(), false, false));
//        fields.add(new IndexField(CarIndex.OFFICIAL_PRICE, spu.getGuidePrice().toString(), false, false));
//
//        BigDecimal toBPrice = convertToBPrice(spu.getGuidePrice());
//        String priceStr = NumberFormatUtils.NumberToString(toBPrice, 0);
//        SPUExtend spuExtend = spu.getSpuExtend();
//        String content = spuExtend.getBrandName() + " " + spu.getName() + " " + priceStr;
//        fields.add(new IndexField(CarIndex.CONTENT, content, false, true));
//
//        Index index = new Index();
//        index.setFields(fields);
//        return index;
//    }

    private BigDecimal convertToBPrice(BigDecimal toCPrice) {
        if (toCPrice == null) {
            return null;
        }
        return toCPrice.divide(new BigDecimal(100));
    }
}
