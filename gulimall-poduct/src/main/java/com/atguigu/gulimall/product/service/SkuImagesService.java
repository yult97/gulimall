package com.atguigu.gulimall.product.service;

import com.atguigu.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.product.entity.SkuImagesEntity;

import java.util.List;
import java.util.Map;

/**
 * sku图片
 *
 * @author yubo
 * @email 1904282367@gmail.com
 * @date 2021-12-18 23:49:40
 */
public interface SkuImagesService extends IService<SkuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * @throws
     * @title saveSkuImages
     * @description 保存sku图片信息
     * @author yubo
     * @updateTime 2023-03-13 21:37
     * @Return void
     * @Param [skuImagesEntities]
     */
    void saveSkuImages(List<SkuImagesEntity> skuImagesEntities);
}

