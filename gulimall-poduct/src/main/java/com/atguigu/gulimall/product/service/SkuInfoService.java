package com.atguigu.gulimall.product.service;

import com.atguigu.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.product.entity.SkuInfoEntity;

import java.util.Map;

/**
 * sku信息
 *
 * @author yubo
 * @email 1904282367@gmail.com
 * @date 2021-12-18 23:49:40
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * @title saveSkuInfo
     * @description TODO
     * @author yubo
     * @updateTime 2023-03-13 0:45
     * @throws
     * @Return void
     * @Param  [skuInfoEntity]
     */
    void saveSkuInfo(SkuInfoEntity skuInfoEntity);
}

