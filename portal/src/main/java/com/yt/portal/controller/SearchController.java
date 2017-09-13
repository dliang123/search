package com.yt.portal.controller;

import com.yt.demo.core.dao.SPUDao;
import com.yt.demo.core.entity.SPUEntity;
import com.yt.ic.search.model.Index;
import com.yt.ic.search.model.SearchMode;
import com.yt.ic.search.service.CreateProductIndexServiceImpl;
import com.yt.ic.search.service.SearchIndexServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * web-api
 * Created by jackdeng on 2017/9/5.
 */
@Controller
@RequestMapping(value = "/api/search")
public class SearchController {

    @Autowired
    private SPUDao spuDao;
    @Autowired
    private CreateProductIndexServiceImpl createProductIndexService;
    @Autowired
    private SearchIndexServiceImpl searchIndexService;


    @ApiOperation("按照品牌创建车系索引")
    @RequestMapping(value = "createSeriesIndex", method = RequestMethod.POST)
    @ResponseBody
    public void createSeriesIndex() {
        createProductIndexService.deleteCarSeriesIndex();
        createProductIndexService.createCarSeriesIndex();
    }

    @ApiOperation("按照品牌创建车系索引")
    @RequestMapping(value = "queryIndexes", method = RequestMethod.GET)
    @ResponseBody
    public List<Index> queryIndexes(@RequestParam String scope,@RequestParam String keyword, @RequestParam String fieldName) throws IOException {
        return searchIndexService.queryIndexes(scope, keyword, fieldName, SearchMode.ANALYZED_AND_ANY_IN, 10);
    }
}
