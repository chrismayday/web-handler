package com.bluexiii.jwh.service;

import com.bluexiii.jwh.dto.GdsDetailDTO;
import com.bluexiii.jwh.util.HttpComponentsUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bluexiii.jwh.util.WoegoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by bluexiii on 2016/9/27.
 */
@Service
public class WoegoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WoegoService.class);
    private ObjectMapper objectMapper = new ObjectMapper();
    private final WoegoUtil woegoUtil;
    private String defaultChnlId = "17b3rov";

    @Autowired
    public WoegoService(WoegoUtil woegoUtil) {
        this.woegoUtil = woegoUtil;
    }

    /**
     * 筛选条件查询(暂不启用）
     *
     * @param chnlId
     * @return
     */
    public Map<String, Object> querySearchConditionList(String chnlId) {
        if (chnlId == null || chnlId.equals("")) {
            chnlId = defaultChnlId;
        }

        String method = "com.ai.woego.gds.info.querySearchConditionList";

        Map<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("chnlId", chnlId);
        paramMap.put("catId", "100");

        return woegoUtil.apiClient(method, paramMap);
    }

    /**
     * 商品清单查询(暂不启用）
     *
     * @param chnlId
     * @param brandId
     * @param pageNum
     * @return
     */
    public Map<String, Object> queryGdsList(String chnlId, String brandId, String pageNum) {
        if (chnlId == null || chnlId.equals("")) {
            chnlId = defaultChnlId;
        }

        String method = "com.ai.woego.gds.info.queryGdsList";

        Map<String, Object> sortMap = new LinkedHashMap<>();
        sortMap.put("sortType", 1);
        sortMap.put("order", "asc");
        String sortStr = "";
        try {
            sortStr = objectMapper.writeValueAsString(sortMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        ArrayList brandListArr = new ArrayList();
        brandListArr.add(brandId);
        Map<String, Object> searchConditionsMap = new LinkedHashMap<>();
        searchConditionsMap.put("brandList", brandListArr);
        String searchConditionsStr = "";
        try {
            searchConditionsStr = objectMapper.writeValueAsString(searchConditionsMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Map<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("chnlId", chnlId);
        paramMap.put("availableFlag", "1");
        paramMap.put("sort", sortStr);
        paramMap.put("search_conditions", searchConditionsStr);
        paramMap.put("pageNum", pageNum);
        paramMap.put("pageSize", "8");

        return woegoUtil.apiClient(method, paramMap);
    }

    /**
     * 商品详情查询
     *
     * @param chnlId
     * @param gdsId
     * @return
     */
    public GdsDetailDTO queryGdsDetail(String gdsId, String chnlId, String shopLocaleCode) {
        //调用接口
        if (chnlId == null || chnlId.equals("")) {
            chnlId = defaultChnlId;
        }
        String method = "com.ai.woego.gds.info.queryGdsDetail";
        Map<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("chnlId", chnlId);
        paramMap.put("gdsId", gdsId);
        JsonNode jsonNode = woegoUtil.apiClientJsonNode(method, paramMap);

        //参数解析
        JsonNode resp = jsonNode.get("resp");
        String code = resp.get("code").asText();
        JsonNode gdsDetails = resp.get("gdsDetails");
        String gdsName = gdsDetails.get("gdsName").asText();
        String gdsSecName = gdsDetails.get("gdsSecName").asText();
        String detailLink = gdsDetails.get("gdsDetatilUrl").asText();
        String specLink = "http://m.woego.cn/goods/querySpecifications?gdsId=" + gdsId + "&catId=100&shopLocaleCode=" + shopLocaleCode;

        JsonNode gdsPics = gdsDetails.get("gdsPics");
        JsonNode gdsPic = gdsPics.get(0);
        String picLink = gdsPic.get("picLink").asText();

        JsonNode skuGdses = gdsDetails.get("skuGds");
        JsonNode skuGds = skuGdses.get(0);
        String gdsPrice = skuGds.get("gdsPrice").asText();
        String gdsRealPrice = skuGds.get("gdsRealPrice").asText();
        String skuId = skuGds.get("skuId").asText();


        //使用GET方式获取规格页HTML
        String specHtml = HttpComponentsUtils.get(specLink);
        //使用JSONP方式获取详情页HTML
        String detailHtmlJsonp = HttpComponentsUtils.get(detailLink);
        String detailHtmlStr = "";
        String detailHtml = "";
        LinkedHashMap detailHtmlMap = null;
        if (detailHtmlJsonp.length() > 5) {
            detailHtmlStr = detailHtmlJsonp.substring(5, detailHtmlJsonp.length() - 1);
            try {
                detailHtmlMap = objectMapper.readValue(detailHtmlStr, LinkedHashMap.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            detailHtml = (String) detailHtmlMap.get("result");
        } else {
            detailHtml = "暂无简介";
        }


        GdsDetailDTO gdsDetailDTO = new GdsDetailDTO();
        gdsDetailDTO.setChnlId(chnlId);
        gdsDetailDTO.setGdsId(gdsId);
        gdsDetailDTO.setSkuId(skuId);
        gdsDetailDTO.setCatId("100");
        gdsDetailDTO.setShopLocaleCode(shopLocaleCode);
        gdsDetailDTO.setGdsName(gdsName);
        gdsDetailDTO.setGdsSecName(gdsSecName);
        gdsDetailDTO.setGdsPrice(gdsPrice);
        gdsDetailDTO.setGdsRealPrice(gdsRealPrice);
        gdsDetailDTO.setPicLink(picLink);
        gdsDetailDTO.setDetailLink(detailLink);
        gdsDetailDTO.setSpecLink(specLink);
        gdsDetailDTO.setSpecHtml(specHtml);
        gdsDetailDTO.setDetailHtml(detailHtml);
        return gdsDetailDTO;
    }


}
