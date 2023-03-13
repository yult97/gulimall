/**
 * Copyright 2020 bejson.com
 */
package com.atguigu.gulimall.product.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author yubo
 * @version 1.0.0
 * @ClassName SpuSaveVo.java
 * @Description 商品保存实体类
 * @createTime 2023年03月09日 22:14:00
 */

@Data
public class SpuSaveVo {

    private String spuName;
    private String spuDescription;
    private Long catalogId;
    private Long brandId;
    private BigDecimal weight;
    private int publishStatus;
    private List<String> decript;
    private List<String> images;
    private Bounds bounds;
    private List<BaseAttrs> baseAttrs;
    private List<Skus> skus;


}
